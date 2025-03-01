package TestSystem;

import Loading_And_Updating.PropertyUpdater;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    public class PropertyUpdaterTest {
    private static final String TEST_FILE_PATH = "test_properties.csv";
    private PropertyUpdater updater;

    @BeforeAll
    public static void setupTestFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write("Address,Size,PricePerSqM,Status\n");
            writer.write("1-1,100,2000,For Sale\n");
            writer.write("2-2,150,2500,For Sale\n");
        }
    }

    @BeforeEach
  public  void setUp() {
        updater = PropertyUpdater.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        PropertyUpdater instance1 = PropertyUpdater.getInstance();
        PropertyUpdater instance2 = PropertyUpdater.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
   public void testUpdatePropertyStatus() throws IOException {
        List<Integer> addressToUpdate = Arrays.asList(1, 1);
        updater.updatePropertyStatus(addressToUpdate, TEST_FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            reader.readLine();
            String updatedProperty = reader.readLine();
            assertTrue(updatedProperty.endsWith("Sold"), "Property should be marked as 'Sold'");
        }
    }

    @Test
   public void testPropertyNotFound() {
        List<Integer> nonExistentAddress = Arrays.asList(9, 9);
        updater.updatePropertyStatus(nonExistentAddress, TEST_FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            reader.readLine();
            String propertyLine = reader.readLine();
            assertFalse(propertyLine.endsWith(",Sold"), "Non-existent property should not be marked as 'Sold'");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void cleanup() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists())
            testFile.delete();
    }
}
