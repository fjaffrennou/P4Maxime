package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public static void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) )
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());

        double inHour = ticket.getInTime().getTime();
        double outHour = ticket.getOutTime().getTime();
        double subtractedTime = (outHour - inHour) / (60 * 60 * 1000);
        double duration;

        if ( subtractedTime < 0.5) {
            duration = 0.00;
        }
        else {
            duration = subtractedTime;
        }

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

}