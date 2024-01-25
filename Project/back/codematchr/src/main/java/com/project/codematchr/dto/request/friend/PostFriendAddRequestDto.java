package com.project.codematchr.dto.request.friend;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostFriendAddRequestDto {
    
    @NotBlank
    private String friendMyEmail;
    @NotBlank
    private String friendEmail;

}
