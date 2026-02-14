package com.example.ecom.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecom.entity.SensorReading;
import com.example.ecom.repo.SensorReadingRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.ecom.responses.SensorReadingResponse;
@RestController
public class DashboardController {
    private final SensorReadingRepo readingRepo;
    public DashboardController(SensorReadingRepo readingRepo){
        this.readingRepo=readingRepo;
    }
    @GetMapping("/api/readings")
    public Page<SensorReadingResponse> getReadings(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("capturedAt").descending());
                //Need to only do it for the user that is logged in!!

        Page<SensorReading> results = readingRepo.findByDevice_User_Username(username, pageable);
        return results.map(a->
            new SensorReadingResponse(
                a.getDevice().getName(),
                a.getSoilMoisture(),
                a.getTemperature(),
                a.getCapturedAt()
            )
        );
    }
        
}
