package br.com.brunomateus.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.brunomateus.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
      @Autowired
      private JWTProvider jwtProvider;
    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
       
       // SecurityContextHolder.getContext().setAuthentication(null);

        if(request.getRequestURI().startsWith("/company") || request.getRequestURI().startsWith("/job") ){
            String header = request.getHeader("Authorization");

            if(header != null){
                var token = this.jwtProvider.validateToken(header);
                
                if(token == null){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase())).toList();

                request.setAttribute("company_id",token.getSubject());
    
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),null,grants);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

       filterChain.doFilter(request, response);
    }
    
}
