package com.project.codematchr.service;

import org.springframework.http.ResponseEntity;

import com.project.codematchr.dto.request.friend.PostFriendAddRequestDto;
import com.project.codematchr.dto.response.friend.GetFriendTotalListResponseDto;
import com.project.codematchr.dto.response.friend.PostFriendAddResponseDto;

public interface FriendService {

  ResponseEntity<? super GetFriendTotalListResponseDto> getFriendTotalList(String friendMyEmail);

  ResponseEntity<? super PostFriendAddResponseDto> postFriendAdd(PostFriendAddRequestDto dto);

}
