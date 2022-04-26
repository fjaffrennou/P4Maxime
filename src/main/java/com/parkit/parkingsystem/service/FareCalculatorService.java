package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import static com.parkit.parkingsystem.util.InputReaderUtil.readVehicleRegistrationNumber;

public class FareCalculatorService {

    public static void calculateFare(Ticket ticket) throws Exception {
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
                ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR)-checkLicencePlateNumberForFivePercentageDiscount());
                break;
            }
            case BIKE: {
                ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR)-checkLicencePlateNumberForFivePercentageDiscount());
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    public static double checkLicencePlateNumberForFivePercentageDiscount() throws Exception {

        Ticket ticket= new Ticket();
        readVehicleRegistrationNumber();

        String listOfLicencePlateNumber = DBConstants.CHECK_TICKET;
        double rateOfDiscount;

        if ((listOfLicencePlateNumber).equals(readVehicleRegistrationNumber())) {
            System.out.println("Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
            rateOfDiscount = 0.05;
        } else {
            rateOfDiscount = 0.00;
        }
        return rateOfDiscount * ticket.getPrice();
    }
}