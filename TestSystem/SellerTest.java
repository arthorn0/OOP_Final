package TestSystem;

import BasicClasses.*;
import Exceptions.PropertyAlreadyExitsException;
import Exceptions.PropertyNotAvailableException;
import Exceptions.PropertyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerTest {
    private Broker broker;
    private Seller seller;
    private Buyer buyer;
    private Apartment apartment;

    @BeforeEach
    void setUp() {
        broker = new Broker("Jim Red", "jim@example.com");
        seller = new Seller("Shani Johnson", "shani@example.com");
        buyer = new Buyer("Alice Smith", "alice@example.com");
    }

    @Test
    void testListProperty() throws PropertyAlreadyExitsException {
        apartment = new Apartment(1, 1, 2000, 100, false);
        seller.listProperty(apartment, broker);
        assertTrue(broker.getAllProperties().contains(apartment));
        assertTrue(seller.getOwnedProperties().contains(apartment));
    }

    @Test
    void testListDuplicateProperty() {
        apartment = new Apartment(2,2,2,2,false);
        assertThrows(PropertyAlreadyExitsException.class, () -> {
            seller.listProperty(apartment, broker);
            seller.listProperty(apartment, broker);
        });
    }

    @Test
    void testRemoveProperty() throws PropertyAlreadyExitsException, PropertyNotFoundException {
        apartment = new Apartment(3,2,2,2,false);
        seller.listProperty(apartment, broker);
        seller.removeProperty(apartment.getAddress(), broker);
        assertFalse(broker.getAllProperties().contains(apartment));
        assertFalse(seller.getOwnedProperties().contains(apartment));
    }

    @Test
    void testRemoveNonExistingProperty() {
        List<Integer> fakeAddress = List.of(2, 2);
        assertThrows(PropertyNotFoundException.class, () -> seller.removeProperty(fakeAddress, broker));
    }

    @Test
    void testSellProperty() throws PropertyAlreadyExitsException {
        apartment = new Apartment(1,2,2,2,false);
        seller.listProperty(apartment, broker);
        assertTrue(seller.sellProperty(broker, apartment, buyer));
        assertTrue(apartment.isSold());
    }

    @Test
    void testSellAlreadySoldProperty() throws PropertyAlreadyExitsException {
        apartment = new Apartment(1,3,2,2,false);
        seller.listProperty(apartment, broker);
        seller.sellProperty(broker, apartment, buyer);

        assertThrows(PropertyNotAvailableException.class, () -> seller.sellProperty(broker, apartment, buyer));
    }
}
