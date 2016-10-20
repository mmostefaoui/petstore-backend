package com.rbcits.sdata.config;

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
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);




        /*
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // allow CORS OPTIONS calls through
                .and()
                .authorizeRequests()
                .antMatchers("/greeting**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
*/
        /*
        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // allow CORS OPTIONS calls through
                .anyRequest().authenticated()
                .and().
                httpBasic().and().
                csrf().disable();
                */
    }

}
