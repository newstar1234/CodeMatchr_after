package com.project.codematchr.service;

import org.springframework.http.ResponseEntity;

import com.project.codematchr.dto.request.friend.PostFriendAddRequestDto;
import com.project.codematchr.dto.response.friend.GetFriendListResponseDto;
import com.project.codematchr.dto.response.friend.PostFriendAddResponseDto;

public interface FriendService {

  ResponseEntity<? super GetFriendListResponseDto> getFriendList(String friendMyEmail);

  ResponseEntity<? super PostFriendAddResponseDto> postFriendAdd(String friendMyEmail, PostFriendAddRequestDto dto);

}
