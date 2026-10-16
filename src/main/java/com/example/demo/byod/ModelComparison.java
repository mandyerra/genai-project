package com.example.demo.byod;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelComparison {

    private final ChatClient chatClient;

    public ModelComparison(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }



}
