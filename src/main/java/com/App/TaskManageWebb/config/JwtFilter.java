package com.App.TaskManageWebb.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//will be executed before spring security filter
//it valisates the jwt token
//provides user details for spring security filter for the authentication
@Component
public class JwtFilter extends OncePerRequestFilter {
    //final better for constructor injection
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService userDetailsService;

    public JwtFilter(JwtTokenProvider jwtTokenProvider, MyUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    //    private final MyUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token =getTokenFromRequest(request);
        //StringUtils.hasText the same of != null
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            //get username from token
            String username = jwtTokenProvider.getUsernameFromToken(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);



    }
    // get jwt token from hhtp request
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        //the authheader always starts with "Bearer " word and after it the token i only want the token
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7 );
        }
        return null ;
    }

    }

