package TestSystem;

import BasicClasses.Apartment;
import BasicClasses.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyTest {
    private Property property;

    @BeforeEach
    void setUp() {
        property = new Apartment(10, 20, 3000.0, 50.0, false);

    }

    @Test
    void testPropertyConstructor_ValidInputs() {
        assertNotNull(property);
        assertEquals(10, ((Apartment) property).getAddress().getFirst()); // Address
        assertEquals(20, ((Apartment) property).getAddress().get(1)); // Avenue
        assertEquals(3000.0, ((Apartment) property).getPricePerSqM());
        assertEquals(50.0, ((Apartment) property).getSize());
        assertFalse(property.isSold());
        assertEquals(0,property.getFullAddress().size());
    }

    @Test
    void testPropertyConstructor_InvalidAddress() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Apartment(-5, 20, 3000.0, 50.0, false)
        );
        assertEquals("Addresses and negative numbers are not Allowed", exception.getMessage());
    }

    @Test
    void testPropertyConstructor_InvalidAvenue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Apartment(10, -2, 3000.0, 50.0, false)
        );
        assertEquals("Addresses and negative numbers are not Allowed", exception.getMessage());
    }

    @Test
    void testSetSold() {
        ((Apartment) property).setSold(true);
        assertTrue(((Apartment) property).isSold());
    }
}
