package com.project.codematchr.controller;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.codematchr.dto.request.compare.PostCompareRequestDto;
import com.project.codematchr.dto.response.compare.GetCompareResponseDto;
import com.project.codematchr.dto.response.compare.PostCompareResponseDto;
import com.project.codematchr.service.CompareService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/compare")
@RequiredArgsConstructor
public class CompareController {
    
    private final CompareService compareService;

    @PostMapping("/{compareUserEmail}")
    public ResponseEntity<? super PostCompareResponseDto> postCompare(@AuthenticationPrincipal String compareUserEmail, @RequestBody @Valid PostCompareRequestDto postCompareRequestDto) {
        ResponseEntity<? super PostCompareResponseDto> responseEntity = compareService.postCompare(compareUserEmail, postCompareRequestDto);
        return responseEntity;
    }

    @GetMapping("/{compareUserEmail}/compareList")
    public ResponseEntity<? super GetCompareResponseDto> getCompareList(@AuthenticationPrincipal String compareUserEmail) {
        ResponseEntity<? super GetCompareResponseDto> responseEntity = compareService.getCompareList(compareUserEmail);
        return responseEntity;
    }

}
