package com.project.codematchr.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import com.project.codematchr.dto.request.friend.PostFriendAddRequestDto;
import com.project.codematchr.entity.pk.FriendPk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "friend")
@Table(name = "friend")
@IdClass(FriendPk.class)
public class FriendEntity {
  
  @Id
  private String friendMyEmail;

  @Id
  private String friendEmail;

  public FriendEntity(String friendMyEmail, PostFriendAddRequestDto dto) {
    this.friendMyEmail = dto.getFriendMyEmail();
    this.friendEmail = dto.getFriendEmail();
  }

}
