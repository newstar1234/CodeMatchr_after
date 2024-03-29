package com.project.codematchr.dto.request.user;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchUserPasswordRequestDto {

    @NotBlank
    private String userPassword;

}
