package TestSystem;

import BasicClasses.Apartment;
import Services.InteriorDesignService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InteriorDesignServiceTest {
    private Apartment apartment;
    private double cost = 200_000;
    @Test
    public void testGetCost() {
        InteriorDesignService service = new InteriorDesignService(apartment);
        assertEquals(201_500, service.getCost() + cost);
    }

    @Test
    public void testGetServiceName() {
        InteriorDesignService service = new InteriorDesignService(apartment);
        assertTrue(service.getServiceName().contains("Interior Design Service"));
    }
}
