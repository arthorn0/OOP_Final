package TestSystem;

import BasicClasses.Apartment;
import SearchStrategy.AvailabilitySearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilitySearchTest {
    private AvailabilitySearch searchStrategy;
    private List<Apartment> apartments;

    @BeforeEach
   public void setUp() {
        searchStrategy = new AvailabilitySearch(Arrays.asList(5, 5), 3);
        apartments = Arrays.asList(
                new Apartment(5, 5, 2000, 100, false),
                new Apartment(6, 6, 2500, 120, false),
                new Apartment(7, 7, 3000, 150, true),
                new Apartment(4, 4, 2200, 110, false)
        );
    }

    @Test
   public void testSearchAvailableApartments() {
        List<Apartment> results = searchStrategy.search(apartments);
        assertEquals(3, results.size());
        assertTrue(results.contains(apartments.get(0)));
        assertTrue(results.contains(apartments.get(3)));
    }
}
