package com.spendwise.controller;

import com.spendwise.dto.AiInputDto;
import com.spendwise.dto.AiTaskDto;
import com.spendwise.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-input")
public class AiController {

    private static final String LOGGED_IN_USER = "a1029488-f4a8-4466-bada-884994cd22ad";
    private final AiService aiService;

    @PostMapping
    public ResponseEntity<AiTaskDto> parseRawText(@RequestBody AiInputDto requestBody) {
        final var response = aiService.save(LOGGED_IN_USER,requestBody);

        return ResponseEntity.ok(response);

    }




}
