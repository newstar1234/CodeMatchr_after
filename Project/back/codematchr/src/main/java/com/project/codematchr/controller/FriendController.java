package com.project.codematchr.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.codematchr.dto.request.friend.PostFriendAddRequestDto;
import com.project.codematchr.dto.response.friend.GetFriendListResponseDto;
import com.project.codematchr.dto.response.friend.PostFriendAddResponseDto;
import com.project.codematchr.service.FriendService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/friend")
@RequiredArgsConstructor
public class FriendController {

  private final FriendService friendService;

  @GetMapping("/{friendMyEmail}")
  public ResponseEntity<? super GetFriendListResponseDto> getFriendTotalList (
    @PathVariable String friendMyEmail
  ) {
    ResponseEntity<? super GetFriendListResponseDto> response = friendService.getFriendList(friendMyEmail);
    return response;
  }

  @PostMapping("/{friendMyEmail}")
  public ResponseEntity<? super PostFriendAddResponseDto> postFriendAdd(
    @AuthenticationPrincipal String friendMyEmail,
    @RequestBody @Valid PostFriendAddRequestDto requestBody
  ) {
    ResponseEntity<? super PostFriendAddResponseDto> response = friendService.postFriendAdd(friendMyEmail, requestBody);
    return response;
  }
 

}
