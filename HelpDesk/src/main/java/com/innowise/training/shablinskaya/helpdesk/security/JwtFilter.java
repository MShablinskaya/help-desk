package com.innowise.training.shablinskaya.helpdesk.security;

import com.innowise.training.shablinskaya.helpdesk.service.impl.UserDetailsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {
    private static final Logger log = org.apache.log4j.Logger.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION = "Authorization";

    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;

    public JwtFilter(){}

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if(token != null && jwtProvider.validateToken(token)) {
            String email = jwtProvider.getLoginFromToken(token);
            JwtUser jwtUser = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(servletRequest, servletResponse);


    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader(AUTHORIZATION);
        if(hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
