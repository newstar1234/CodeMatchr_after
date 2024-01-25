package com.project.codematchr.service.implement;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.codematchr.dto.request.friend.PostFriendAddRequestDto;
import com.project.codematchr.dto.response.ResponseDto;
import com.project.codematchr.dto.response.friend.FriendListResponseDto;
import com.project.codematchr.dto.response.friend.GetFriendTotalListResponseDto;
import com.project.codematchr.dto.response.friend.PostFriendAddResponseDto;
import com.project.codematchr.entity.FriendEntity;
import com.project.codematchr.entity.FriendViewEntity;
import com.project.codematchr.repository.FriendRepository;
import com.project.codematchr.repository.FriendViewRepository;
import com.project.codematchr.repository.UserRepository;
import com.project.codematchr.service.FriendService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendServicdImplement implements FriendService {

  private final FriendRepository friendRepository;
  private final FriendViewRepository friendViewRepository;
  private final UserRepository userRepository;
  
  @Override
  public ResponseEntity<? super GetFriendTotalListResponseDto> getFriendTotalList(String friendMyEmail) {

    List<FriendListResponseDto> friendList = null;

    try {

      List<FriendViewEntity> friendViewEntities = friendViewRepository.findByFriendMyEmail(friendMyEmail);

      friendList = FriendListResponseDto.copyFriendList(friendViewEntities);
      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return GetFriendTotalListResponseDto.success(friendList);

  }

  @Override
  public ResponseEntity<? super PostFriendAddResponseDto> postFriendAdd(PostFriendAddRequestDto dto) {

    try {

      FriendEntity friendEntity = new FriendEntity(dto);
      friendRepository.save(friendEntity);

      
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
    return PostFriendAddResponseDto.success();

  }

  
}