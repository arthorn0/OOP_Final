package TestSystem;

import BasicClasses.*;
import Exceptions.PropertyAlreadyExitsException;
import Loading_And_Updating.PropertyLoader;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropertyLoaderTest {
    private static final String TEST_FILE_PATH = "test_properties.csv";
    private PropertyLoader loader;
    private Broker broker;
    private Seller seller;

    @BeforeAll
    void setupTestFile() throws IOException {
        File testFile = new File(TEST_FILE_PATH);
        if (!testFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
                writer.write("Address,Size,PricePerSqM,Status\n");
                writer.write("1-1,100,2000,false\n");

            }
        }
    }
    @BeforeEach
    void setUp() {
        loader = PropertyLoader.getInstance();
        broker = new Broker("Test Broker", "broker@test.com");
        seller = new Seller("Test Seller", "seller@test.com");
    }

    @Test
    void testSingletonInstance() {
        PropertyLoader instance1 = PropertyLoader.getInstance();
        PropertyLoader instance2 = PropertyLoader.getInstance();
        assertSame(instance1, instance2); // Ensure singleton behavior
    }

    @Test
    void testLoadProperties() throws IOException {
        loader.loadProperties(TEST_FILE_PATH, broker, seller);

        List<Apartment> allProperties = broker.getAllProperties();
        assertEquals(1, allProperties.size());

        Apartment apt1 = broker.getPropertyByAddress("1-1");


        assertNotNull(apt1);

        assertEquals(100, apt1.getSize());

        assertFalse(apt1.isSold());
    }

    @Test
    void testPreventDuplicateProperties() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH, true));
        writer.write("1-1,120,2300,false\n");
        loader.loadProperties(TEST_FILE_PATH,broker,seller);

         assertThrows(PropertyAlreadyExitsException.class,() -> {
             writer.write("1-1,120,2300,false\n");
             loader.loadProperties(TEST_FILE_PATH,broker,seller);
         });

    }

    @Test
    void testHandleInvalidFile() {
        assertDoesNotThrow(() -> loader.loadProperties("invalid_path.csv", broker, seller));
    }

    @AfterAll
    void cleanup() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) testFile.delete();
    }
}
