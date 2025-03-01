package TestSystem;

import BasicClasses.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static class TestUser extends User {
        public TestUser(String name, String contactInfo) {
            super(name, contactInfo);
        }
    }

    @Test
    public void testUserConstructor() {
        User user = new TestUser("Example", "Example@example.com");

        assertNotNull(user);
        assertEquals("Example", user.getName());
        assertEquals("Example@example.com", user.getContactInfo());
    }

}
