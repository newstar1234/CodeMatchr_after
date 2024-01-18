package com.project.codematchr.config;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.codematchr.filter.JwtAuthenticationFilter;
import com.project.codematchr.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .cors(cors -> cors
              .configurationSource(corsConfigurationSource())
            )
            .csrf(CsrfConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .sessionManagement(sesstionManagement -> sesstionManagement
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(request -> request
              .requestMatchers("/", "/api/v1/authentication/**", "oauth2/**", "/api/v1/user/*", "/api/v1/file/**").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/v1/board/**", "/api/v1/room/current-room/*").permitAll()
              .requestMatchers(HttpMethod.GET, "/api/v1/room/**" , "/api/v1/user/*", "/api/v1/friend/**").permitAll()
              .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
              .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/user"))
              .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
              .userInfoEndpoint(endpoint -> endpoint.userService(defaultOAuth2UserService))
              .successHandler(oAuth2SuccessHandler)
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
              .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.addAllowedOrigin("*");
      configuration.addAllowedMethod("*");
      configuration.addAllowedHeader("*");

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);

      return source;
    }

}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

@Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
  throws IOException, ServletException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed\" }");

  }
  
}
