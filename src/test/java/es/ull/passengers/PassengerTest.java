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
    public void testConstructorWithInvalidCountryCode() {
        // Arrange
        String identifier = "XXX999";
        String name = "Luke Dorty";
        String invalidCountryCode = "INVALID";

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            new Passenger(identifier, name, invalidCountryCode);
        });
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
        // Arrange
        Flight previousFlight = new Flight("CD456", 30);
        Flight newFlight = new Flight("EF789", 40);
        Passenger passenger = new Passenger("ABC123", "John Doe", "US");

        // Act
        passenger.joinFlight(previousFlight);
        passenger.joinFlight(newFlight);

        // Assert
        assertEquals(newFlight, passenger.getFlight());
        assertEquals(0, previousFlight.getNumberOfPassengers());

        // añado pasajero duplicado
        passenger.joinFlight(newFlight);
        assertEquals(newFlight, passenger.getFlight()); // pasajero se queda
        assertEquals(0, previousFlight.getNumberOfPassengers()); // Vuelvo anterior vacío
        assertEquals(1, newFlight.getNumberOfPassengers()); // 1 pasajero, el mismo
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
    void testFailJoinFlight() {
        Flight flight = new Flight("AB123", 2);
        Passenger passenger = new Passenger("12345678A", "Jack Harlow", "US");
        passenger.joinFlight(flight);
        Passenger passenger2 = new Passenger("12345678A", "Jack Harlow", "US");
        assertDoesNotThrow(() -> passenger2.joinFlight(flight));
        Passenger passenger3 = new Passenger("12345678A", "Jack Harlow", "US");
        assertThrows(RuntimeException.class, () -> passenger3.joinFlight(flight));
        Flight flight2 = new Flight("AB124", 2);
        assertThrows(RuntimeException.class, () -> passenger3.joinFlight(flight2));

    }




}
