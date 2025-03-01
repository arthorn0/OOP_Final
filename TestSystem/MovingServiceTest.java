package TestSystem;

import BasicClasses.Apartment;
import Services.ApartmentDecorator;
import Services.MovingService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovingServiceTest {
    private Apartment apartment;
    private double cost = 100_000;
    @Test
    public void testGetCost() {
        MovingService service = new MovingService(apartment);
        assertEquals(101_000, service.getCost() + cost);
    }

    @Test
    public void testGetServiceName() {
        MovingService service = new MovingService(apartment);
        assertTrue(service.getServiceName().contains("Moving Service"));
    }
}
