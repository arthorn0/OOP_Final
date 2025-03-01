package TestSystem;

import BasicClasses.Broker;
import BasicClasses.Buyer;
import BasicClasses.Seller;
import BasicClasses.User;
import Exceptions.UserException;
import Factories.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
   public void testCreateBroker() {
        User broker = new Broker("example", "example@example.com");
        User createdUser = UserFactory.createUser(broker);
        assertInstanceOf(Broker.class, createdUser);
        assertEquals("example", createdUser.getName());
        assertEquals("example@example.com", createdUser.getContactInfo());
    }

    @Test
   public void testCreateSeller() {
        User seller = new Seller("example", "example@example.com");
        User createdUser = UserFactory.createUser(seller);
        assertInstanceOf(Seller.class, createdUser);
        assertEquals("example", createdUser.getName());
        assertEquals("example@example.com", createdUser.getContactInfo());
    }

    @Test
    public void testCreateBuyer() {
        User buyer = new Buyer("example", "example@example.com");
        User createdUser = UserFactory.createUser(buyer);
        assertInstanceOf(Buyer.class, createdUser);
        assertEquals("example", createdUser.getName());
        assertEquals("example@example.com", createdUser.getContactInfo());
    }

    @Test
    public void testCreateInvalidUser() {
        Exception exception = assertThrows(UserException.class, () -> UserFactory.createUser(null));
        assertEquals(" User type not recognized: null", exception.getMessage());
    }
}
