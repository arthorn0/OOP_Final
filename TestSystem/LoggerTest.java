package TestSystem;

import BasicClasses.Logger;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoggerTest {
    private Logger logger;
    private static final String TEST_CREDENTIALS_FILE = "resources/test_user_credentials.txt";

    @BeforeAll
    void setupTestFile() throws IOException {
        // Ensure test directory exists
        Path testFilePath = Path.of(TEST_CREDENTIALS_FILE);
        Files.createDirectories(testFilePath.getParent());

        // Clear the test file before each test
        Files.write(testFilePath, "".getBytes());
    }

    @BeforeEach
    void setUp() {
        logger = Logger.getInstance();
    }

    @Test
    void testRegisterSuccess() {
        logger.register("testUser", "securePass", "securePass");
        assertTrue(logger.login("testUser", "securePass"));
    }

    @Test
    void testRegisterDuplicateUsername() {
        logger.register("duplicateUser", "password123", "password123");
        assertFalse(logger.login("duplicateUser", "wrongPassword"));
    }

    @Test
    void testRegisterShortPassword() {
        logger.register("shortPassUser", "123", "123");
        assertFalse(logger.login("shortPassUser", "123"));
    }

    @Test
    void testRegisterPasswordMismatch() {
        logger.register("mismatchUser", "password1", "password2");
        assertFalse(logger.login("mismatchUser", "password1"));
    }

    @Test
    void testLoginSuccess() {
        logger.register("validUser", "validPass", "validPass");
        assertTrue(logger.login("validUser", "validPass"));
    }

    @Test
    void testLoginFail() {
        assertFalse(logger.login("nonExistentUser", "randomPass"));
    }

    @Test
    void testLogout() {
        logger.register("logoutUser", "password123", "password123");
        assertTrue(logger.login("logoutUser", "password123"));
        logger.logout();
        assertNull(logger.getCurrentUser());
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_CREDENTIALS_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
