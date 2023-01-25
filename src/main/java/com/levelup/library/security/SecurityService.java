package com.levelup.library.security;

import com.levelup.library.controllers.util.RequestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = false,
        securedEnabled = false,
        jsr250Enabled = true )
public class SecurityService
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RequestController requestFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity ) throws Exception
    {
        httpSecurity.csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling().authenticationEntryPoint( new AuthenticationEntryPoint() )
                .and().sessionManagement()
                .sessionCreationPolicy( SessionCreationPolicy.STATELESS );

        httpSecurity.addFilterBefore( requestFilter, UsernamePasswordAuthenticationFilter.class );
        return httpSecurity.build();
    }

    @Bean( name = BeanIds.AUTHENTICATION_MANAGER )
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
}