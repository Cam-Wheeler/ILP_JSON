package dev.cameron.logistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// DTO for the driver notifications service. We use this in endpoint two
public class DriverNotificationsDTO {

    private String driverId;
    @JsonProperty("name")
    private String driverName;
    @JsonProperty("email")
    private String driverEmail;
    @JsonProperty("phone")
    private String driverPhone;
    @JsonProperty("status")
    private String truckStatus;
    private int numOfPackages; // The number of packages in their truck.

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public int getNumOfPackages() {
        return numOfPackages;
    }

    public void setNumOfPackages(int numOfPackages) {
        this.numOfPackages = numOfPackages;
    }

    public String getTruckStatus() {
        return truckStatus;
    }

    public void setTruckStatus(String truckStatus) {
        this.truckStatus = truckStatus;
    }

    @Override
    public String toString() {
        return "DriverNotificationsDTO{" +
                "driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverEmail='" + driverEmail + '\'' +
                ", driverPhone='" + driverPhone + '\'' +
                ", numOfPackages=" + numOfPackages +
                ", truckStatus='" + truckStatus + '\'' +
                '}';
    }

}
