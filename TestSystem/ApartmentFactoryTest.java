package TestSystem;

import BasicClasses.Apartment;
import Factories.ApartmentFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApartmentFactoryTest {

    @Test
    public void testCreateApartmentValidInput() {
        List<Integer> address = Arrays.asList(5, 10);
        double pricePerSqM = 3000;
        double size = 120;

        Apartment apartment = ApartmentFactory.createApartment(address, pricePerSqM, size);

        assertNotNull(apartment);
        assertEquals(5, apartment.getAddress().get(0));
        assertEquals(10, apartment.getAddress().get(1));
        assertEquals(pricePerSqM, apartment.getPricePerSqM());
        assertEquals(size, apartment.getSize());
        assertFalse(apartment.isSold());
    }

    @Test
    public void testCreateApartmentWithInvalidAddress() {
        List<Integer> invalidAddress = List.of(5);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ApartmentFactory.createApartment(invalidAddress, 2500, 100));

        assertEquals("Address list must contain at least an avenue and a street.", exception.getMessage());
    }

    @Test
    public void testCreateApartmentWithNullAddress() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> ApartmentFactory.createApartment(null, 2000, 80));

        assertEquals("Address list must contain at least an avenue and a street.", exception.getMessage());
    }


}
