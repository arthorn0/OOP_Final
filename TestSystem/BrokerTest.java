package TestSystem;

import BasicClasses.Apartment;
import BasicClasses.Broker;
import SearchStrategy.SearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestBroker {
    private Broker broker;
    private Apartment apartment1;
    private Apartment apartment2;
    private Apartment apartment3;

    @BeforeEach
    void setUp() {
        broker = new Broker("John Doe", "john@example.com");
        apartment1 = new Apartment(3, 5, 2000, 100, false);
        apartment2 = new Apartment(4, 6, 2500, 120, false);
        apartment3 = new Apartment(3, 7, 1800, 90, true); // Sold apartment
    }

    @Test
    void testBrokerConstructor() {
        Broker newBroker = new Broker("Alice Doe", "alice@example.com");

        assertNotNull(newBroker);
        assertEquals("Alice Doe", newBroker.getName());
        assertEquals("alice@example.com", newBroker.getContactInfo());
        assertTrue(newBroker.getAllProperties().isEmpty());
    }

    @Test
    void testAddProperty() {
        assertTrue(broker.addProperty(apartment1));
        assertTrue(broker.addProperty(apartment2));
        assertFalse(broker.addProperty(apartment1)); // Duplicate property should return false
    }

    @Test
    void testRemoveProperty() {
        broker.addProperty(apartment1);
        assertTrue(broker.removeProperty(apartment1.getAddress()));
        assertFalse(broker.removeProperty(apartment1.getAddress())); // Already removed
    }

    @Test
    void testGetAvailableProperties() {
        broker.addProperty(apartment1);
        broker.addProperty(apartment2);
        broker.addProperty(apartment3); // Sold apartment

        List<Apartment> available = broker.getAvailableProperties();
        assertEquals(2, available.size());
        assertTrue(available.contains(apartment1));
        assertTrue(available.contains(apartment2));
        assertFalse(available.contains(apartment3)); // Sold property should not be listed
    }

    @Test
    void testPropertyExists() {
        broker.addProperty(apartment1);
        assertTrue(broker.propertyExists(apartment1));
        assertFalse(broker.propertyExists(apartment2));
    }

    @Test
    void testGetAllProperties() {
        broker.addProperty(apartment1);
        broker.addProperty(apartment2);
        broker.addProperty(apartment3);
        List<Apartment> allProperties = broker.getAllProperties();

        assertEquals(3, allProperties.size());
        assertTrue(allProperties.contains(apartment1));
        assertTrue(allProperties.contains(apartment2));
        assertTrue(allProperties.contains(apartment3));
    }

    @Test
    void testGetPropertyByAddress() {
        broker.addProperty(apartment1);
        broker.addProperty(apartment2);

        String addressKey = "3-5";
        Apartment found = broker.getPropertyByAddress(addressKey);

        assertNotNull(found);
        assertEquals(apartment1, found);
    }

    @Test
    void testUpdateMethod() {
        broker.update("New property added!");
        broker.update("Property sold!");
        assertDoesNotThrow(() -> broker.update("Update received."));
    }

    @Test
    void testSearchWithMockStrategy() {
        broker.addProperty(apartment1);
        broker.addProperty(apartment2);

        SearchStrategy searchStrategy = properties -> properties.stream()
                .filter(apartment -> apartment.getPricePerSqM() > 2000)
                .toList();

        List<Apartment> results = broker.search(searchStrategy);
        assertEquals(1, results.size());
        assertTrue(results.contains(apartment2));
    }
}
