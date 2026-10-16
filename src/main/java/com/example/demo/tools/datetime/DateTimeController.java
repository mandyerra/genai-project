package com.example.demo.tools.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeController {

    private final ChatClient chatClient;

    public DateTimeController(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultSystem("You are a helpful AI assistant.")

            .build();
    }
    @GetMapping("/getCurrentDateTime")
    public String getCurrentDateTime(@RequestParam("message") String message){
        return chatClient.prompt()
                .user(message)
                .tools(new DateTimeTools())
                .call()
                .content();
    }
}
