package server.service;

import api.service.CabBookingService;
import api.exception.BookingException;
import api.model.Booking;

public class CabBookingServiceImpl implements CabBookingService {

    @Override public Booking bookRide(String pickUpLocation) throws BookingException {
//        if (random() < 0.3) throw new BookingException("Cab unavailable");
        return new Booking("test booking");
    }
}
