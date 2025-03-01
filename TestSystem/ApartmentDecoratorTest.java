package TestSystem;

import BasicClasses.Apartment;
import Services.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ApartmentDecoratorTest {

    @Test
    public void testApartmentDecoratorConstructor() {
        Apartment eveningServiceApt = new EveningService(new Apartment(1, 1, 200, 200, false));
        Apartment movingServiceApt = new MovingService(new Apartment(1, 1, 200, 200, false));
        Apartment interiorDesignApt = new InteriorDesignService(new Apartment(1, 1, 200, 200, false));
        Apartment cleaningServiceApt = new CleaningService(new Apartment(1, 1, 200, 200, false));

        assertEquals(40000 + 800, eveningServiceApt.calculateTotalPrice());
        assertEquals(40000 + 1000, movingServiceApt.calculateTotalPrice());
        assertEquals(40000 + 2000, interiorDesignApt.calculateTotalPrice());
        assertEquals(40000 + 500, cleaningServiceApt.calculateTotalPrice());
    }



}
