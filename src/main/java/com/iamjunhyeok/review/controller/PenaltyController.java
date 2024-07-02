package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.PenaltySearchResponse;
import com.iamjunhyeok.review.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PenaltyController {

    private final PenaltyService penaltyService;

    @GetMapping("/{userId}/penalties")
    public ResponseEntity<List<PenaltySearchResponse>> search(@PathVariable Long userId) {
        return ResponseEntity.ok(
                penaltyService.search(userId)
                        .stream()
                        .map(PenaltySearchResponse::from)
                        .toList()
        );
    }

    @GetMapping("/me/penalties")
    public ResponseEntity<List<PenaltySearchResponse>> search() {
        // 로그인 기능 개발되면 수정할 것!!
        return ResponseEntity.ok(
                penaltyService.search(1L)
                        .stream()
                        .map(PenaltySearchResponse::from)
                        .toList()
        );
    }

    @DeleteMapping("/{userId}/penalties/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId, @PathVariable Long id) {
        penaltyService.delete(userId, id);
    }
}
