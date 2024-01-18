package com.project.codematchr.service.implement;

import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.codematchr.entity.CustomOAuth2User;
import com.project.codematchr.entity.UserEntity;
import com.project.codematchr.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
  
  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

    OAuth2User oAuth2User = super.loadUser(request);
    String oauthClientName = request.getClientRegistration().getClientName();

    try {
      System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
    } catch (Exception exception) {
      exception.printStackTrace();
    }

    UserEntity userEntity = null;
    String userNickname = null;
    String userEmail = null;

    if(oauthClientName.equals("kakao")) {
      userNickname = "kakao_" + oAuth2User.getAttributes().get("id");
      userEmail = "kakao_" + oAuth2User.getAttributes().get("email");
      userEntity = new UserEntity(userEmail, userNickname, "kakao");
    }

    if(oauthClientName.equals("naver")) {
      Map
      <String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
      userNickname = "naver_" + responseMap.get("id").substring(0, 14);
      userEmail = responseMap.get("email");
      userEntity = new UserEntity(userEmail, userNickname, "naver");
    }

    userRepository.save(userEntity);

    return new CustomOAuth2User(userNickname);


  }

}
