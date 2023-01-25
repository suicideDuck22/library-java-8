package com.levelup.library.controllers.util;

import com.levelup.library.security.AuthenticateService;
import com.levelup.library.security.UserDetails;
import com.levelup.library.security.UserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter("/*")
public class RequestController extends OncePerRequestFilter {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private UserDetailsService userDetailsService;

    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, jakarta.servlet.FilterChain filterChain ) throws ServletException, IOException
    {
        System.out.println("Entrou no Filter");
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userEmail = null;

        if( authorizationHeader != null )
        {
            token = authorizationHeader.replace("Bearer ", "");
            userEmail = authenticateService.verifyToken( token );
        }

        if( userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername( userEmail );

            if( authenticateService.verifyUserDetails( userEmail, userDetails ))
            {
                setAuthentication( httpServletRequest, userDetails );
            }
        }

        httpServletResponse.addHeader( "Access-Control-Allow-Origin", "*" );
        filterChain.doFilter( httpServletRequest, httpServletResponse );
    }

    private void setAuthentication( HttpServletRequest request, UserDetails userDetails )
    {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
        usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
    }
}
