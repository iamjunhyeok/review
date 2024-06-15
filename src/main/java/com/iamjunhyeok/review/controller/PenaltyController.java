package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.PenaltySearchResponse;
import com.iamjunhyeok.review.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;

    @GetMapping("/{userId}/penalties")
    public ResponseEntity<List<PenaltySearchResponse>> search(@PathVariable Long userId) {
        return ResponseEntity.ok(
                penaltyService.search(userId)
                        .stream().map(penalty -> PenaltySearchResponse.from(penalty))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/me/penalties")
    public ResponseEntity<List<PenaltySearchResponse>> search() {
        // 로그인 기능 개발되면 수정할 것!!
        return ResponseEntity.ok(
                penaltyService.search(1L)
                        .stream().map(penalty -> PenaltySearchResponse.from(penalty))
                        .collect(Collectors.toList())
        );
    }
}
