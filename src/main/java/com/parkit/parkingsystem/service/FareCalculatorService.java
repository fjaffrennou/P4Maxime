package com.parkit.parkingsystem.service;
import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.parkit.parkingsystem.service.ParkingService.checkLicencePlateNumber;


public class FareCalculatorService {

    private static final Logger logger = LogManager.getLogger("FareCalculatorService");

    public static void calculateFare(Ticket ticket) throws Exception {

        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())))
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());

        double inHour = ticket.getInTime().getTime();
        double outHour = ticket.getOutTime().getTime();
        double subtractedTime = (outHour - inHour) / (60 * 60 * 1000);
        double duration;

        if (subtractedTime < 0.5) {
            duration = 0.00;
        } else {
            duration = subtractedTime;
        }
        fivePercentageDiscount();
//ticket.getPrice()
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice((duration * Fare.CAR_RATE_PER_HOUR) - ( fivePercentageDiscount() * (duration * Fare.CAR_RATE_PER_HOUR) ));
                break;
            }
            case BIKE: {
                ticket.setPrice((duration * Fare.BIKE_RATE_PER_HOUR) - ( fivePercentageDiscount() * (duration * Fare.CAR_RATE_PER_HOUR) ));
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

   public static double fivePercentageDiscount() {

       double rateOfDiscount;

       if (ParkingService.checkLicencePlateNumber==true) {
           rateOfDiscount = 0.05;
       } else {
           rateOfDiscount = 0.00;
       }
       return rateOfDiscount;
   }
}
