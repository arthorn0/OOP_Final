package TestSystem;

import BasicClasses.Apartment;
import Services.EveningService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EveningServiceTest {
    private Apartment apartment;
    private double apartmentCost = 200_000;
    @Test
    public void testGetCost() {
        EveningService service = new EveningService(apartment);
        assertEquals(200_800, service.getCost() + apartmentCost);
    }

    @Test
    public void testGetServiceName() {
        EveningService service = new EveningService(apartment);
        assertTrue(service.getServiceName().contains("Evening Service"));
    }
}
