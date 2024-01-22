package com.project.codematchr.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.codematchr.common.response.ResponseCode;
import com.project.codematchr.common.response.ResponseMessage;
import com.project.codematchr.dto.response.ResponseDto;
import com.project.codematchr.entity.CommentEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCommentResponseDto extends ResponseDto {
  
  private int commentNumber;
  private int commentBoardNumber;
  private String commentUserEmail;
  private String commentContents;
  private String commentWriteDatetime;

  private GetCommentResponseDto(String code, String message, CommentEntity commentEntity) {
    super(code, message);
    this.commentNumber = commentEntity.getCommentNumber();
    this.commentBoardNumber = commentEntity.getCommentBoardNumber();
    this.commentUserEmail = commentEntity.getCommentUserEmail();
    this.commentContents = commentEntity.getCommentContents();
    this.commentWriteDatetime = commentEntity.getCommentWriteDatetime();
  } 

  public static ResponseEntity<GetCommentResponseDto> success(CommentEntity commentEntity) {
    GetCommentResponseDto result = new GetCommentResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, commentEntity);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> noExistedCommentNumber() {
    ResponseDto result = new ResponseDto(ResponseCode.NO_EXISTED_COMMENT_NUMBER, ResponseMessage.NO_EXISTED_COMMENT_NUMBER);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
}

}
