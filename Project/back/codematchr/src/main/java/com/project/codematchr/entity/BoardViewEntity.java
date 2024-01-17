package com.project.codematchr.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_view")
@Table(name = "board_view")
public class BoardViewEntity {

  @Id
  private int boardNumber;
  private String boardTitle;
  private String boardContents;
  private String boardImageUrl;
  private int boardViewCount;
  private int boardCommentCount;
  private int boardFavoriteCount;
  private String boardWriteDatetime;
  private String userNickname;
  private String userProfileImageUrl;
  private String userEmail;

}
