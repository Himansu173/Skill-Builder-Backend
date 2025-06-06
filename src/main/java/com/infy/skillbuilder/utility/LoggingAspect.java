package com.infy.skillbuilder.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.infy.skillbuilder.exception.SkillBuilderException;

@Component
@Aspect
public class LoggingAspect {

    private static final Log LOGGER = LogFactory.getLog(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.infy.skillbuilder.service.*Impl.*(..))", throwing = "exception")
    public void logServiceException(SkillBuilderException exception) {
        LOGGER.error(exception.getMessage());
    }
}
