package com.example.deathparade.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**.
 *
 */

@Aspect
@Component
public class LogAspect {

  private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

  /**.
   *
   */

  @Before("execution(* com.example.deathparade..*(..))")
  public void logBefore(JoinPoint joinPoint) {
    if (logger.isDebugEnabled()) {
      logger.info("CALL: {}", joinPoint.getSignature().toShortString());
    }
  }

  /**.
   *
   */

  @AfterReturning(pointcut = "execution(* com.example.deathparade..*(..))", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    if (logger.isDebugEnabled()) {
      logger.info("EXECUTED: {} RETURNED: {}",
          joinPoint.getSignature().toShortString(), result);
    }
  }

  /**.
   *
   */

  @AfterThrowing(pointcut = "execution(* com.example.deathparade..*(..))", throwing = "error")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
    if (logger.isDebugEnabled()) {
      logger.error("OCCURRED: {} REASON: {}",
          joinPoint.getSignature().toShortString(), error.getMessage());
    }
  }
}
