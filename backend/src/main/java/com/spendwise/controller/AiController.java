package com.spendwise.controller;

import com.spendwise.dto.AiActiveTaskDto;
import com.spendwise.dto.AiInputDto;
import com.spendwise.dto.AiTaskDto;
import com.spendwise.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-input")
public class AiController {
    
    private final AiService aiService;

    @PostMapping
    public ResponseEntity<AiTaskDto> parseRawText(@RequestBody AiInputDto requestBody, @AuthenticationPrincipal String userId) {
        final var response = aiService.save(userId,requestBody);
        return ResponseEntity.ok(response);

    }
    
    @GetMapping("/active")
    public ResponseEntity<List<AiActiveTaskDto>> getActiveTasks(@AuthenticationPrincipal String userId) {
        final var activeTasks = aiService.getActiveTasks(userId);
        return ResponseEntity.ok(activeTasks);
    }




}
