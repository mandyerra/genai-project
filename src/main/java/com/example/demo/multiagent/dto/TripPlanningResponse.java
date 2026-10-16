package com.example.demo.multiagent.dto;

public class TripPlanningResponse {
    private final TripRequest tripRequest;
    private final ResearchResult researchResult;
    private final BudgetResult budgetResult;
    private final ItineraryResult itineraryResult;
    private final ReviewResult reviewResult;
    private final String status;

    public TripPlanningResponse(TripRequest tripRequest, ResearchResult researchResult,
                                BudgetResult budgetResult, ItineraryResult itineraryResult, ReviewResult reviewResult, String status) {
        this.tripRequest = tripRequest;
        this.researchResult = researchResult;
        this.budgetResult = budgetResult;
        this.itineraryResult = itineraryResult;
        this.reviewResult = reviewResult;
        this.status = status;
    }

    //getters and setters
    public TripRequest getTripRequest() {
        return tripRequest;
    }

    public ResearchResult getResearchResult() {
        return researchResult;
    }

    public BudgetResult getBudgetResult() {
        return budgetResult;
    }

    public ItineraryResult getItineraryResult() {
        return itineraryResult;
    }

    public ReviewResult getReviewResult() {
        return reviewResult;
    }

    public String getStatus() {
        return status;
    }
}
