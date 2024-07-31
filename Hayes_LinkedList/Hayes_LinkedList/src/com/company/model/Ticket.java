package com.company.model;

import java.io.Serializable;

public class Ticket implements Serializable {
    public static final int ATTRIBUTES_COUNT = 5;

    private String seatNumber;
    private String flightNumber;
    private String passengerName;
    private String passengerNationality;
    private String passengerPhone;

    public static final int TOTAL_SEATS = 100;

    public Ticket(String seatNumber, String flightNumber, String passengerName, String passengerNationality, String passengerPhone) {
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.passengerNationality = passengerNationality;
        this.passengerPhone = passengerPhone;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setPassengerNationality(String passengerNationality) {
        this.passengerNationality = passengerNationality;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerNationality() {
        return passengerNationality;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }
}
