package com.example.demo.multiagent.service.impl;

import com.example.demo.multiagent.Agent;
import com.example.demo.multiagent.dto.BudgetResult;
import com.example.demo.multiagent.dto.TripState;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BudgetAgent implements Agent<TripState, BudgetResult> {

    private final ChatClient chatClient;

    public BudgetAgent(@Qualifier("budgetChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Override
    public String name() {
        return "budget-agent";
    }

    @Override
    public BudgetResult execute(TripState state) {
        return chatClient
                .prompt()
                .user("User request: %s" +
                        "Research produced by the research agent: %s"+
                        "Estimate accommodation, transportation, food and activity costs." +
                        "The total budget must not exceed %s."
                                .formatted(state.request(),
                                        state.research(),
                                        state.request().budget()
                                ))
                .call()
                .entity(BudgetResult.class);

    }
}
