package api.service;

import api.exception.BookingException;
import api.model.Booking;

public interface CabBookingService {
    Booking bookRide(String pickUpLocation) throws BookingException;
}