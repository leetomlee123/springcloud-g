package com.example.invoke.config;


import com.example.invoke.filter.TokenAuthenticationFilter;
import com.example.invoke.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author lee
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService jwtUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder(12, new SecureRandom("leetomlee123".getBytes())));
    }

    @Autowired
    @Qualifier("userTokenService")
    UserTokenService userTokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<RequestMatcher> csrfMethods = new ArrayList<>();
        Arrays.asList("POST", "PUT", "PATCH", "DELETE")
                .forEach(method -> csrfMethods.add(new AntPathRequestMatcher("/**", method)));
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/health",
                        "/webjars/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/movies/**").permitAll()
                .antMatchers("/miuis/**").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new TokenAuthenticationFilter(userTokenService, jwtUserDetailsService), BasicAuthenticationFilter.class);
        http.csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter will ignore the below paths
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/login"
        );
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"

        );

    }
}
