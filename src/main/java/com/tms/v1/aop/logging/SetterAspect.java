package com.tms.v1.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class SetterAspect {
    private final Environment env;
    private final Logger log = LoggerFactory.getLogger(SetterAspect.class);
    public SetterAspect(Environment env) {
        this.env = env;
    }

    
    @Around("execution(public *  save())")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	 try {
             Object result = joinPoint.proceed();
             log.debug("save method >>>>>>>>>>>>>>>>>>>>>>"+result);
             return result;
         } catch (IllegalArgumentException e) {
        	 
             throw e;
         }
    }
}
