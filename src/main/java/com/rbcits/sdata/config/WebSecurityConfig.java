package com.rbcits.sdata.config;

import com.rbcits.sdata.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@ComponentScan("com.rbcits.sdata.security")
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users**").hasAnyAuthority("ADMIN_UNLIMITED_PRIVILEGE", "USER_FIND_PRIVILEGE")
                .antMatchers(HttpMethod.POST, "/api/users/*").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // allow CORS OPTIONS calls through
                .antMatchers("/*").permitAll()
                .and()
                .httpBasic()
                .and()
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
