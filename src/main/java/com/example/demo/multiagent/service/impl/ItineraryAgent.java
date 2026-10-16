package com.example.demo.multiagent.service.impl;

import com.example.demo.multiagent.Agent;
import com.example.demo.multiagent.dto.ItineraryResult;
import com.example.demo.multiagent.dto.TripState;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ItineraryAgent implements Agent<TripState, ItineraryResult> {

    private final ChatClient chatClient;

    public ItineraryAgent(@Qualifier("itineraryChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String name() {
        return "itinerary-agent";
    }

    @Override
    public ItineraryResult execute(TripState state) {
        return chatClient
                .prompt()
                .user(user ->user
                        .text("Create a {days}- day itinerary." +
                                "Request: {request}" +
                                "Research: {research}" +
                                "Budget: {budget}" +
                                "The total estimated cost must remain within the approved budget.")
                        .param("days", state.request().numberOfDays())
                        .param("request", state.request())
                        .param("research", state.research())
                        .param("budget", state.budget()))
                .call()
                .entity(ItineraryResult.class);
    }
}
