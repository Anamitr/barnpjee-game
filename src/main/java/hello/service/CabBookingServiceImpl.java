package hello.service;

import hello.exception.BookingException;
import hello.model.Booking;

import static java.lang.Math.random;
import static java.util.UUID.randomUUID;

public class CabBookingServiceImpl implements CabBookingService{

    @Override public Booking bookRide(String pickUpLocation) throws BookingException {
//        if (random() < 0.3) throw new BookingException("Cab unavailable");
        return new Booking("test booking");
    }
}
