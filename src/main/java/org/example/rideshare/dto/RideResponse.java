package org.example.rideshare.dto;

import org.example.rideshare.model.Ride;
import java.util.Date;

public class RideResponse {
    
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private String status;
    private Date createdAt;
    
    public RideResponse() {
    }
    
    public RideResponse(Ride ride) {
        this.id = ride.getId();
        this.userId = ride.getUserId();
        this.driverId = ride.getDriverId();
        this.pickupLocation = ride.getPickupLocation();
        this.dropLocation = ride.getDropLocation();
        this.status = ride.getStatus();
        this.createdAt = ride.getCreatedAt();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDriverId() {
        return driverId;
    }
    
    public void setDriverId(String driverId) {
        this.driverId = driverId;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
