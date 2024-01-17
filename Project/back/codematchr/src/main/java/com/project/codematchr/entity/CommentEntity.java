package com.project.codematchr.entity;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.project.codematchr.dto.request.board.PostcommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int commentNumber;
  private int commentBoardNumber;
  private String commentUserEmail;
  private String commentContents;
  private String commentWriteDatetime;

  public CommentEntity(Integer commentBoardNumber, String commentUserEmail, PostcommentRequestDto dto) {

    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String writeDatetime = simpleDateFormat.format(now);

    this.commentBoardNumber = commentBoardNumber;
    this.commentUserEmail = commentUserEmail;
    this.commentContents = dto.getCommentContents();
    this.commentWriteDatetime = writeDatetime;
    
  }

}
