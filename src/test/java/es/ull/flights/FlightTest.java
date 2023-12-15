package es.ull.flights;

import es.ull.passengers.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight flight;

    @BeforeEach
    void setUp() {
        // Configuración común antes de cada prueba
        flight = new Flight("AB123", 50); // Número de vuelo válido y 50 asientos
    }

    @Test
    void testGetFlightNumber() {
        assertEquals("AB123", flight.getFlightNumber());
    }

    @Test
    void testGetNumberOfPassengersInitiallyZero() {
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testAddPassenger() {
        Passenger passenger = new Passenger("12345678A", "Mike Towers", "PR");
        assertTrue(flight.addPassenger(passenger));
        assertEquals(1, flight.getNumberOfPassengers());
    }

    @Test
    void testAddPassengerExceedingSeats() {
        // Llenar todos los asientos
        for (int i = 0; i < 50; i++) {
            flight.addPassenger(new Passenger("Passenger " + i, "Luke Skywalker", "IT"));
        }

        // Intentar agregar un pasajero adicional debería lanzar una excepción
        assertThrows(RuntimeException.class, () -> flight.addPassenger(new Passenger("Extra Passenger", "Luke Skywalker", "IT")));
    }

    @Test
    void testRemovePassenger() {
        Passenger passenger = new Passenger("12345678A", "John Doe", "US");
        flight.addPassenger(passenger);
        assertTrue(flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
    }

    @Test
    void testRemovePassengerNotInFlight() {
        Passenger passenger = new Passenger("12345678A", "John Doe", "US");

        // Intentar eliminar un pasajero que no está en el vuelo no debería lanzar excepción
        assertFalse(flight.removePassenger(passenger));
        assertEquals(0, flight.getNumberOfPassengers());
    }
    @Test
    void testInvalidFlightNumber() {
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> new Flight("InvalidFlightNumber", 50));
        assertEquals("Invalid flight number", exception.getMessage());
    }

}
