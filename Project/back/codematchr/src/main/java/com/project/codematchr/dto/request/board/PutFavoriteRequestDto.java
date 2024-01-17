package com.project.codematchr.dto.request.board;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PutFavoriteRequestDto {
  
  @NotBlank
  private String favoriteUserEmail;

}
