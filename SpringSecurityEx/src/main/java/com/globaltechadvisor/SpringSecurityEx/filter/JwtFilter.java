package com.globaltechadvisor.SpringSecurityEx.filter;

import com.globaltechadvisor.SpringSecurityEx.service.JWTService;
import com.globaltechadvisor.SpringSecurityEx.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//For every request we want to be executed only once
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    // We are using context to get object of userDetails,
    //Note: We are not using it directly using @Autowired for getting object of UserDetails because it will create cyclic loop
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // from client, we get "Bearer <token>"
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }

        //username is Not null and User is not authenticated earlier
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

            // We are using context to get object of userDetails,
            //Note: We are not using it directly using @Autowired for getting object of UserDetails because it will create cyclic loop
            // We are not directly getting the object but object of User also using the function passing username
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            // check weather token is valid and the user should be part of database
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //autToken knows about user but don't know about request(which has lot of data)
                //so we are giving details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Once everything is verified we are adding token in chains
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //Now go for next filter
        filterChain.doFilter(request,response);
    }
}
