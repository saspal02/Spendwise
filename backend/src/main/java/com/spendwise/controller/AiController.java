package com.spendwise.controller;

import com.spendwise.service.ai.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai-input")
public class AiController {

    private final AiService aiService;


}
