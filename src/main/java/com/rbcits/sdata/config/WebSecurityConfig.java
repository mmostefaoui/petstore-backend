package com.rbcits.sdata.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {


   /*     http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and().
                httpBasic().and().
                csrf().disable();
                */

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/app/users/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().fullyAuthenticated()
                .and().httpBasic()
                .and().csrf().disable();

    }
}
