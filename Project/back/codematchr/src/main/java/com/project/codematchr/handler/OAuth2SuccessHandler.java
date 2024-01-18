package com.project.codematchr.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.codematchr.entity.CustomOAuth2User;
import com.project.codematchr.provider.JwtProvider;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  
  private final JwtProvider jwtProvider;

  @Override
	public void onAuthenticationSuccess(
      HttpServletRequest request, 
      HttpServletResponse response,
			Authentication authentication
  ) throws IOException, ServletException {

    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    String userId = oAuth2User.getName();
    String token = jwtProvider.create(userId);

    try {
      response.sendRedirect("http://localhost:3000/auth/oauth-response/" + token + "/3600");
    } catch (java.io.IOException exception) {
      exception.printStackTrace();
    }

	}

}
