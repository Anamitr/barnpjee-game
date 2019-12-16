package hello.model;

import java.io.Serializable;

public class Booking implements Serializable {
    private String bookingCode;

    public Booking(String code) {
        bookingCode = code;
    }

    @Override public String toString() {
        return String.format("Ride confirmed: code '%s'.", bookingCode);
    }



    // standard getters/setters and a constructor
}