package binhtt.filter;

import binhtt.components.JwtTokenUtil;
import binhtt.models.User;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${apiPrefix}")
    private  String apiPrefix;
    private final UserDetailsService userDetailsService;
    private  final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

       if (isByPassToken(request)){
           filterChain.doFilter(request,response);
       }

       final  String authHeader = request.getHeader("Authorization");
       if (authHeader != null && authHeader.startsWith("Bearer ")){
           final  String token = authHeader.substring(7);
           final String phoneNumber =  jwtTokenUtil.extractPhoneNumber(token);
           if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication()==null){
               User userDetail  = (User) userDetailsService.loadUserByUsername(phoneNumber);
               if (jwtTokenUtil.validateToken(token,userDetail)){
                   UsernamePasswordAuthenticationToken authenticationToken=
                           new UsernamePasswordAuthenticationToken(userDetail,
                                   null,
                                   userDetail.getAuthorities());
                   authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }
           }

       }
        filterChain.doFilter(request,response);

    }
    private  boolean isByPassToken(@NonNull HttpServletRequest request){
        final List<Pair<String,String>> byPassTokens = Arrays.asList(
                Pair.of(String.format("%s/products",apiPrefix),"GET"),
                Pair.of(String.format("%s/categories",apiPrefix),"GET"),
                Pair.of(String.format("%s/users/register",apiPrefix),"POST"),
                Pair.of(String.format("%s/users/login",apiPrefix),"POST")
        );

        for (Pair<String,String> byPassToken: byPassTokens ) {
            if (request.getServletPath().contains(byPassToken.getLeft()) && request.getMethod().equals(byPassToken.getRight())) {
                return  true;
            }

        }
        return  false;
    }
}
