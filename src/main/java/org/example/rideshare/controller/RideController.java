package org.example.rideshare.controller;

import jakarta.validation.Valid;
import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {
    
    private final RideService rideService;
    
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }
    
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody CreateRideRequest request,
            Authentication authentication) {
        
        String userId = (String) authentication.getDetails();
        RideResponse response = rideService.createRide(request, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/user/rides")
    public ResponseEntity<List<RideResponse>> getUserRides(Authentication authentication) {
        String userId = (String) authentication.getDetails();
        List<RideResponse> rides = rideService.getUserRides(userId);
        return ResponseEntity.ok(rides);
    }
    
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<RideResponse>> getPendingRides() {
        List<RideResponse> rides = rideService.getPendingRides();
        return ResponseEntity.ok(rides);
    }
    
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable String rideId,
            Authentication authentication) {
        
        String driverId = (String) authentication.getDetails();
        RideResponse response = rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId,
            Authentication authentication) {
        
        String userId = (String) authentication.getDetails();
        RideResponse response = rideService.completeRide(rideId, userId);
        return ResponseEntity.ok(response);
    }
}
