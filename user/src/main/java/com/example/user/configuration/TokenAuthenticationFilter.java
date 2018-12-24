package com.example.user.configuration;

import com.example.user.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lee
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("userTokenService")
    UserTokenService userTokenService;

    private UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(UserTokenService tokenHelper,
                                     UserDetailsService userDetailsService) {
        this.userTokenService = tokenHelper;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

            String username;
            String authToken = userTokenService.getToken(request);

            if (authToken != null) {
                username = userTokenService.getUsernameFromToken(authToken);
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (userTokenService.validateToken(authToken, userDetails)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            chain.doFilter(request, response);
        }


}