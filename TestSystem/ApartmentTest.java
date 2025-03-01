package TestSystem;

import BasicClasses.Apartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ApartmentTest {
    private Apartment apartment;
    private Apartment subApartment1;
    private Apartment subApartment2;

    @BeforeEach
    public void setUp() {
        apartment = new Apartment(3, 5, 200000.0, 50.0, false);
        subApartment1 = new Apartment(3, 6, 150000.0, 30.0, false);
        subApartment2 = new Apartment(3, 7, 160000.0, 35.0, false);
    }

    @Test
    public void testGetParentApartment() {
        apartment.addSubApartment(subApartment1);
        assertEquals(apartment, subApartment1.getParentApartment());
    }

    @Test
    public void testAddSubApartment() {
        apartment.addSubApartment(subApartment1);
        assertEquals(1, apartment.getSubCount());
        assertTrue(apartment.getSubApartments().contains(subApartment1));
    }

    @Test
   public void testGetSubApartments() {
        apartment.addSubApartment(subApartment1);
        apartment.addSubApartment(subApartment2);
        List<Apartment> subApartments = apartment.getSubApartments();
        assertEquals(2, subApartments.size());
        assertTrue(subApartments.contains(subApartment1));
        assertTrue(subApartments.contains(subApartment2));
    }

    @Test
   public void testCalculateTotalPrice() {
        apartment.addSubApartment(subApartment1);
        apartment.addSubApartment(subApartment2);
        double expectedTotal = (200000.0 * 50.0) + (150000.0 * 30.0) + (160000.0 * 35.0);
        assertEquals(expectedTotal, apartment.calculateTotalPrice());
    }

    @Test
    public void testGetAddress() {
        List<Integer> address = apartment.getAddress();
        assertEquals(List.of(3, 5), address);
    }

    @Test
    public void testSetSold() {
        apartment.setSold(true);
        assertTrue(apartment.isSold());
    }

    @Test
    public void testGetPricePerSqM() {
        assertEquals(200000.0, apartment.getPricePerSqM());
    }

    @Test
    public void testIsSold() {
        assertFalse(apartment.isSold());
    }

    @Test
    public void testGetSize() {
        assertEquals(50.0, apartment.getSize());
    }

    @Test
   public void testGetSubCount() {
        apartment.addSubApartment(subApartment1);
        apartment.addSubApartment(subApartment2);
        assertEquals(2, apartment.getSubCount());
    }

    @Test
  public void testToString() {
        String result = apartment.toString();
        assertTrue(result.contains("Address: [3, 5]"));
        assertTrue(result.contains("Total Price"));
        assertTrue(result.contains("Status: For Sale"));
    }

    @Test
   public void testEquals() {
        Apartment sameApartment = new Apartment(3, 5, 200000.0, 50.0, false);
        assertEquals(apartment, sameApartment);
    }

    @Test
   public void testHashCode() {
        Apartment sameApartment = new Apartment(3, 5, 200000.0, 50.0, false);
        assertEquals(apartment.hashCode(), sameApartment.hashCode());
    }
}
