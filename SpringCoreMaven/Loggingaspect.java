package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * LoggingAspect – cross-cutting concern that adds logging to all service methods.
 *
 * This single aspect covers two exercises:
 *
 *  Exercise 3  – @Around advice logs the execution time of every
 *                service method (track method execution times).
 *
 *  Exercise 8  – @Before and @After advice logs entry and exit of
 *                every service method (basic AOP separation of concerns).
 *
 * Pointcut expression used throughout:
 *   execution(* com.library.service.*.*(..))
 *   └─ matches any method (*), in any class (*) inside com.library.service,
 *      with any return type (*) and any arguments (..)
 *
 * The aspect is registered as a Spring bean in applicationContext.xml and
 * activated by <aop:aspectj-autoproxy/>.
 */
@Aspect
public class LoggingAspect {

    private static final String POINTCUT = "execution(* com.library.service.*.*(..))";

    // ── Exercise 8: @Before – runs before the target method executes ─────────

    /**
     * Logs the name of every service method before it is called.
     */
    @Before(POINTCUT)
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP - Before] Entering method: "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    // ── Exercise 8: @After – runs after the target method executes ───────────

    /**
     * Logs the name of every service method after it completes
     * (whether normally or by throwing an exception).
     */
    @After(POINTCUT)
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AOP - After]  Exiting  method: "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
    }

    // ── Exercise 3: @Around – wraps the method to measure execution time ─────

    /**
     * Measures and logs the wall-clock execution time of every service method.
     *
     * @param joinPoint provides access to the intercepted method and its args
     * @return the original return value of the method
     * @throws Throwable re-throws any exception the method raises
     */
    @Around(POINTCUT)
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // ← the actual method runs here
        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - startTime;

        System.out.println("[AOP - Around] "
                + joinPoint.getSignature().getName()
                + " executed in " + timeTaken + " ms");

        return result;
    }
}
