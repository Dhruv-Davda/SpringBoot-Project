package org.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRideRequest {
    
    @NotBlank(message = "Pickup location is required")
    @Size(min = 2, message = "Pickup location must be at least 2 characters")
    private String pickupLocation;
    
    @NotBlank(message = "Drop location is required")
    @Size(min = 2, message = "Drop location must be at least 2 characters")
    private String dropLocation;
    
    public CreateRideRequest() {
    }
    
    public CreateRideRequest(String pickupLocation, String dropLocation) {
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
    }
    
    public String getPickupLocation() {
        return pickupLocation;
    }
    
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    
    public String getDropLocation() {
        return dropLocation;
    }
    
    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }
}
