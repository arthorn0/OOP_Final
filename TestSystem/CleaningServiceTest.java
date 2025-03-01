package TestSystem;

import BasicClasses.Apartment;
import Services.CleaningService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CleaningServiceTest {
    private Apartment apartment;
    private double apartmentCost = 500_000;
    @Test
    public void testGetCost() {
        CleaningService service = new CleaningService(apartment);
        assertEquals(500_500, service.getCost() + apartmentCost);
    }

    @Test
    public void testGetServiceName() {
        CleaningService service = new CleaningService(apartment);
        assertTrue(service.getServiceName().contains("Cleaning Service"));
    }
}
