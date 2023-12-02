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

    @Test
    void testGetFlightInitiallyNull() {
        assertNull(passenger.getFlight());
    }

    @Test
    void testInvalidCountryCode() {
        assertThrows(RuntimeException.class, () -> new Passenger("19875678A", "Luke Dorty", "XX"));
    }

    @Test
    void testValidCountryCode() {
        assertDoesNotThrow(() -> new Passenger("19875678A", "Luke Dorty", "US"));
    }

    @Test
    void testJoinFlight() {
        Flight flight = new Flight("AB123", 50);
        passenger.joinFlight(flight);
        assertEquals(flight, passenger.getFlight());
    }

    @Test
    void testJoinFlightWithPreviousFlight() {
        Flight previousFlight = new Flight("CD456", 30);
        passenger.joinFlight(previousFlight);

        Flight newFlight = new Flight("EF789", 40);
        passenger.joinFlight(newFlight);

        assertEquals(newFlight, passenger.getFlight());
        assertEquals(0, previousFlight.getNumberOfPassengers());
    }

    @Test
    void testJoinFlightExceedingSeats() {
        Flight flight = new Flight("AB123",3 );
        passenger.joinFlight(flight);

        Passenger passenger2 = new Passenger("87654321B", "Francesco Virgolini", "IT");
        Passenger passenger3 = new Passenger("09876543T", "Miguel Torres", "PR");
        passenger3.joinFlight(flight);
        passenger2.joinFlight(flight);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> new Passenger("99999999C", "Bob Smith", "US").joinFlight(flight));

        assertEquals("Not enough seats for flight AB123", exception.getMessage());
        assertEquals(3, flight.getNumberOfPassengers()); // Verificar que el número de pasajeros no cambió
        assertThrows(RuntimeException.class, () -> new Passenger("87654321I", "Dimitri Vegas Smith", "RO").joinFlight(flight));
    }

    @Test
    void testSetFlight() {
        Flight flight = new Flight("AB123", 50);
        passenger.setFlight(flight);
        assertEquals(flight, passenger.getFlight());
    }

    @Test
    void testToString() {
        assertEquals("Passenger Jack Harlow with identifier: 12345678A from US", passenger.toString());
    }

    @Test
    void testJoinFlightFailedAddPassenger() {
        Flight flight = new Flight("AB123", 2);

        Passenger passenger1 = new Passenger("12345678A", "Miguel Sanders", "BR");
        Passenger passenger2 = new Passenger("87654321B", "Jose Dorta", "VE");
        Passenger passenger3 = new Passenger("82736272X", "Jack Smith", "US");

        passenger1.joinFlight(flight);
        passenger2.joinFlight(flight);

        assertThrows(RuntimeException.class, () -> passenger3.joinFlight(flight));
    }
}
