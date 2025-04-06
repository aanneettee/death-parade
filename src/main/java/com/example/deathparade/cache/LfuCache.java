package com.example.deathparade.cache;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**.
 *
 */

@Slf4j
public abstract class LfuCache<T> {

  private final int maxCapacity;
  private final Map<Long, CacheEntry<T>> cache = new HashMap<>();

  /**.
   *
   */

  protected static class CacheEntry<T> {
    T value;
    int frequency;

    CacheEntry(T value) {
      this.value = value;
      this.frequency = 1;
    }
  }

  /**.
   *
   */

  protected LfuCache(int maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  /**.
   *
   */

  public synchronized T get(Long id) {
    CacheEntry<T> entry = cache.get(id);
    if (entry != null) {
      entry.frequency++;
      log.info(
              "Cache hit: Retrieved item with ID {} from cache (frequency: {})",
              id,
              entry.frequency
      );
      return entry.value;
    }
    return null;
  }

  /**.
   *
   */

  public synchronized void put(Long id, T value) {
    if (cache.containsKey(id)) {
      CacheEntry<T> entry = cache.get(id);
      entry.value = value;
      entry.frequency++;
      log.info(
              "Cache update: Updated item with ID {} in cache (frequency: {})",
              id,
              entry.frequency
      );
    } else {
      if (cache.size() >= maxCapacity) {
        evictLeastFrequentlyUsed();
      }
      cache.put(id, new CacheEntry<>(value));
      log.info("Cache add: Added item with ID {} to cache", id);
    }
  }

  private void evictLeastFrequentlyUsed() {
    Long lfuKey = null;
    int minFrequency = Integer.MAX_VALUE;

    for (Map.Entry<Long, CacheEntry<T>> entry : cache.entrySet()) {
      if (entry.getValue().frequency < minFrequency) {
        minFrequency = entry.getValue().frequency;
        lfuKey = entry.getKey();
      }
    }
    if (lfuKey != null) {
      cache.remove(lfuKey);
      log.info(
              "Cache eviction: Removed least frequently used item with ID {} (frequency: {})",
              lfuKey,
              minFrequency
      );
    }
  }

  /**.
   *
   */

  public synchronized void remove(Long id) {
    if (cache.remove(id) != null) {
      log.info("Cache remove: Removed item with ID {} from cache", id);
    }
  }

  /**.
   *
   */

  public synchronized void clear() {
    cache.clear();
    log.info("Cache cleared: All items removed");
  }
}