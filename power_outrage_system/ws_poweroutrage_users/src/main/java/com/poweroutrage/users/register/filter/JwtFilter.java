package com.poweroutrage.users.register.filter;

import static com.poweroutrage.users.register.constant.UsersConstants.HEADER;
import static com.poweroutrage.users.register.constant.UsersConstants.TOKEN_PREFIX;
import com.poweroutrage.users.register.service.LoginService;
import com.poweroutrage.users.register.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.Table;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LoginService userDetails;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader(HEADER);
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {

            token = authorizationHeader.substring(7, authorizationHeader.length());
            System.out.print(token);
            try{
                userName = jwtUtil.extractUsername(token);
            }catch(IllegalArgumentException e){
                throw new SignatureException("an error occured during getting" +
                        " username from token");
            }catch(ExpiredJwtException e){
                throw new SignatureException("an error occured during getting" +
                        " username from token");
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetails.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
