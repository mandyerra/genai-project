package com.example.demo.multiagent.controller;

import com.example.demo.multiagent.dto.TripPlanningResponse;
import com.example.demo.multiagent.dto.TripRequest;
import com.example.demo.multiagent.service.impl.TripPlanningOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripPlanningOrchestrator tripPlanningOrchestrator;

    public TripController(TripPlanningOrchestrator tripPlanningOrchestrator) {
        this.tripPlanningOrchestrator = tripPlanningOrchestrator;
    }

    @GetMapping("/plan")
    public ResponseEntity<TripPlanningResponse> plan(@RequestBody TripRequest request){
        TripPlanningResponse tripPlanningResponse = tripPlanningOrchestrator.plan(request);

        return  ResponseEntity.ok(tripPlanningResponse);
    }
}
