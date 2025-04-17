package com.PseudoNerds.LandNFT.Controller;

import com.PseudoNerds.LandNFT.Service.ChatBotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/gemini")
public class ChatBotController {

    private final ChatBotService geminiService;

    public ChatBotController(ChatBotService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        String reply = geminiService.chat(userMessage);
        return ResponseEntity.ok(reply);
    }
}

