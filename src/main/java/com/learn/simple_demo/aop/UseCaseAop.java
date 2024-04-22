package com.learn.simple_demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import static com.learn.simple_demo.Context.*;

@Aspect
@Component
public class UseCaseAop {

    // Matches all methods in classes annotated with @UseCase
    @Around("within(@com.learn.simple_demo.annotation.UseCase *)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            startTransactionManager();  // Before advice logic
            result = joinPoint.proceed();  // Proceed with the original method execution
            commitTransactionManager();  // After advice logic
        } catch (Throwable ex) {
            rollbackTransactionManager();  // AfterThrowing advice logic
            throw ex;  // Rethrow the exception to maintain the method's contract
        }
        return result;
    }
}
