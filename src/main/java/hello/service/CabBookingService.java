package hello.service;

import hello.exception.BookingException;
import hello.model.Booking;

public interface CabBookingService {
    Booking bookRide(String pickUpLocation) throws BookingException;
}