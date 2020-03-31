package com.tms.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.tms.v1.aop.logging.SetterAspect;

import io.github.jhipster.config.JHipsterConstants;

@Configuration
@EnableAspectJAutoProxy
public class SetterAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public SetterAspect setterAspect(Environment env) {
        return new SetterAspect(env);
    }
}
