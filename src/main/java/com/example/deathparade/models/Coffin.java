package com.example.deathparade.models;

/**.
 *
 */

public class Coffin {
  public final String outsideColor;
  public String outsideMaterial;
  public String insideMaterial;
  public String insideColor;
  public CoffinParameters parameters;
  public int cost;
  /**.
   *
   */

  public Coffin(String outsideColor, String outsideMaterial,
                 String insideMaterial, String insideColor, CoffinParameters parameters, int cost) {
    this.outsideColor = outsideColor;
    this.outsideMaterial = outsideMaterial;
    this.insideMaterial = insideMaterial;
    this.insideColor = insideColor;
    this.cost = cost;
  }

  public String getOutsideColor() {
    return outsideColor;
  }

  public String getOutsideMaterial() {
    return outsideMaterial;
  }

  public String getInsideMaterial() {
    return insideMaterial;
  }

  public String getInsideColor() {
    return insideColor;
  }

  public CoffinParameters getParameters() {
    return parameters;
  }

  public int getCost() {
    return cost;
  }
}
