package es.ull.passengers;

import es.ull.flights.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassengerTest {

    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("12345678A", "Jack Harlow", "US");
    }

    @Test
    void testGetters() {
        assertEquals("12345678A", passenger.getIdentifier());
        assertEquals("Jack Harlow", passenger.getName());
        assertEquals("US", passenger.getCountryCode());
    }


}
