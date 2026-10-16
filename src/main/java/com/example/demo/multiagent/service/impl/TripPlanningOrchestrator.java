package com.example.demo.multiagent.service.impl;

import com.example.demo.multiagent.dto.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.multiagent.dto.*;

import java.util.List;


@Service
public class TripPlanningOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(TripPlanningOrchestrator.class);

    private final ResearchAgent researchAgent;
    private final BudgetAgent budgetAgent;
    private final ItineraryAgent itineraryAgent;
    private final ReviewAgent reviewAgent;

    public TripPlanningOrchestrator(ResearchAgent researchAgent,
                                    BudgetAgent budgetAgent,
                                    ItineraryAgent itineraryAgent,
                                    ReviewAgent reviewAgent) {
        this.researchAgent = researchAgent;
        this.budgetAgent = budgetAgent;
        this.itineraryAgent = itineraryAgent;
        this.reviewAgent = reviewAgent;
    }

    public TripPlanningResponse plan(TripRequest request){
        log.info("Trip planning started: destination={}, days={}", request.destination(), request.numberOfDays());

        TripState state = TripState.start(request);
        ResearchResult researchResult = researchAgent.execute(request);
        state = state.withResearch(researchResult);

        BudgetResult budgetResult = budgetAgent.execute(state);
        state = state.withBudget(budgetResult);

        if(budgetResult.totalCost().intValue() > request.budget().intValue()) {
            log.warn("trip exceeds budget: requestedBudget={}," +
                    "estimatedCost={}", request.budget(), budgetResult.totalCost());

            ReviewResult reviewResult = new ReviewResult(false, "The proposed trip exceeds the available budget",
                    List.of("Estimated trip cost is higher than the requested budget"),
                    List.of("Reduce accommodation cost",
                            "Remove lower priority activities",
                            "Use public transport"));
        }

        ItineraryResult itinerary = itineraryAgent.execute(state);
        state = state.withItinerary(itinerary);

        ReviewResult reviewResult = reviewAgent.execute(state);

        log.info("Trip planning workflow completed: " +
                "destination={}, valid={}",
                request.destination(), reviewResult.valid());

        return new TripPlanningResponse(request,
                researchResult, budgetResult,
                itinerary, reviewResult, reviewResult.valid()? "COMPLETED" : "REVIEW_FAILED");

    }
}


