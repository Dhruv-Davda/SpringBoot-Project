package org.example.rideshare.service;

import org.example.rideshare.dto.CreateRideRequest;
import org.example.rideshare.dto.RideResponse;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.exception.NotFoundException;
import org.example.rideshare.model.Ride;
import org.example.rideshare.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {
    
    private final RideRepository rideRepository;
    
    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
    
    public RideResponse createRide(CreateRideRequest request, String userId) {
        Ride ride = new Ride(
            userId,
            request.getPickupLocation(),
            request.getDropLocation()
        );
        
        Ride savedRide = rideRepository.save(ride);
        return new RideResponse(savedRide);
    }
    
    public List<RideResponse> getUserRides(String userId) {
        List<Ride> rides = rideRepository.findByUserId(userId);
        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<RideResponse> getPendingRides() {
        List<Ride> rides = rideRepository.findByStatus("REQUESTED");
        return rides.stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }
    
    public RideResponse acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));
        
        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status. Current status: " + ride.getStatus());
        }
        
        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");
        
        Ride updatedRide = rideRepository.save(ride);
        return new RideResponse(updatedRide);
    }
    
    public RideResponse completeRide(String rideId, String userId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));
        
        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride must be in ACCEPTED status to complete. Current status: " + ride.getStatus());
        }
        
        if (!userId.equals(ride.getUserId()) && !userId.equals(ride.getDriverId())) {
            throw new BadRequestException("You are not authorized to complete this ride");
        }
        
        ride.setStatus("COMPLETED");
        
        Ride updatedRide = rideRepository.save(ride);
        return new RideResponse(updatedRide);
    }
    
    public RideResponse getRideById(String rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found with id: " + rideId));
        return new RideResponse(ride);
    }
}
