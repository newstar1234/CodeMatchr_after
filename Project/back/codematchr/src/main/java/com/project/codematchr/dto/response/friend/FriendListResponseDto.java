package com.project.codematchr.dto.response.friend;
import java.util.ArrayList;
import java.util.List;
import com.project.codematchr.entity.FriendViewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendListResponseDto {

  // todo : friendMyEmail를 넘겨줄 필요가 있는지 다시 생각해보기 //
  private String friendMyEmail;
  private String friendEmail;
  private String nickname;
  private String stateMessage;
  private String profileImageUrl;

  public FriendListResponseDto(FriendViewEntity friendViewEntity) {
    this.friendMyEmail = friendViewEntity.getFriendMyEmail();
    this.friendEmail = friendViewEntity.getFriendEmail();
    this.nickname = friendViewEntity.getNickname();
    this.stateMessage = friendViewEntity.getStateMessage();
    this.profileImageUrl = friendViewEntity.getProfileImageUrl();
  }

  public static List<FriendListResponseDto> copyFriendList(List<FriendViewEntity> friendViewEntities) {
    List<FriendListResponseDto> friendList = new ArrayList<>();

    for(FriendViewEntity friendViewEntity: friendViewEntities) {
      FriendListResponseDto friend = new FriendListResponseDto(friendViewEntity);
      friendList.add(friend);
    }

    return friendList;
  }

}
