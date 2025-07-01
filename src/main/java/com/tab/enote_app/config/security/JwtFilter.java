package com.tab.enote_app.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tab.enote_app.handler.GenericResponse;
import com.tab.enote_app.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            String token=null;
            String username=null;

            if(authHeader!=null && authHeader.startsWith("Bearer ")){
                 token = authHeader.substring(7);
                 username = jwtService.extractUsername(token);
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Boolean isTokenValid = jwtService.validateToken(token, userDetails);

                if(isTokenValid){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
        } catch (Exception e) {

           responseGenerator(response,e);
           return;
        }

        filterChain.doFilter(request,response);

    }

    private void responseGenerator(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Object error = GenericResponse.builder()
                .status("failed")
                .message(e.getMessage())
                .responseStatus(HttpStatus.UNAUTHORIZED)
                .build().create().getBody();

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));

    }
}
