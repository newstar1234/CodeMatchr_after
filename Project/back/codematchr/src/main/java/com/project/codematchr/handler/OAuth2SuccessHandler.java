package com.project.codematchr.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.codematchr.entity.CustomOAuth2User;
import com.project.codematchr.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  
  // 성공했을때 //

  private final JwtProvider jwtProvider;

  @Override
	public void onAuthenticationSuccess(
      HttpServletRequest request, 
      HttpServletResponse response,
			Authentication authentication
  ) throws IOException, ServletException {

    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    String userNickname = oAuth2User.getName();
    String token = jwtProvider.create(userNickname);

    response.sendRedirect("http://localhost:3000/authentication/oauth-response/" + token + "/3600");   
    
	}

}
