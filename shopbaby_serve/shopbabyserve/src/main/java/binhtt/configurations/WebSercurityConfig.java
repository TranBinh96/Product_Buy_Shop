package binhtt.configurations;

import binhtt.filter.JwtTokenFilter;
import binhtt.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSercurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${apiPrefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/categories", apiPrefix),
                                    String.format("%s/products", apiPrefix)
                            )
                            .permitAll()

                            .requestMatchers(POST,
                                    String.format("%s/categories", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(PUT,
                                    String.format("%s/categories", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE,
                                    String.format("%s/categories", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(POST,
                                    String.format("%s/products", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(PUT,
                                    String.format("%s/products", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(DELETE,
                                    String.format("%s/products", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .anyRequest().authenticated();

                })
        ;
        return http.build();
    }
}
