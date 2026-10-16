package com.example.demo.multiagent.service.impl;

import com.example.demo.multiagent.Agent;
import com.example.demo.multiagent.dto.ItineraryResult;
import com.example.demo.multiagent.dto.ReviewResult;
import com.example.demo.multiagent.dto.TripState;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReviewAgent implements Agent<TripState, ReviewResult> {

    private final ChatClient chatClient;

    public ReviewAgent(@Qualifier("reviewChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Override
    public String name() {
        return "review-agent";
    }

    @Override
    public ReviewResult execute(TripState state) {
        return chatClient
                .prompt()
                .user(user -> user
                        .text("Review the following trip plan" +
                                "Original request: {request}" +
                                "Research: {research}" +
                                "Budget: {budget}" +
                                "Itinerary: {itinerary}" +
                                "Verify:" +
                                "- The itinerary has correct number of days." +
                                "- The total cost is within budget." +
                                "- The activities are practical" +
                                "- The itinerary is consistent with the research")
                        .param("request", state.request())
                        .param("research", state.research())
                        .param("budget", state.budget())
                        .param("itinerary", state.itinerary()))
                .call()
                .entity(ReviewResult.class);
    }
}
