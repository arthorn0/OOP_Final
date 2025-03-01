package TestSystem;

import BasicClasses.Apartment;
import BasicClasses.Buyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BuyerTest {
    private Buyer buyer;
    private Apartment forSaleApartment;
    private Apartment soldApartment;

    @BeforeEach
     public void set(){
        buyer = new Buyer("example","example@gamil.com");
        forSaleApartment = new Apartment(1,1,1,2,false);
        soldApartment = new Apartment(1,2,3,1,true);
    }


    @Test
    public void constructorTest() {
        assertEquals("example", buyer.getName());
        assertEquals("example@gamil.com", buyer.getContactInfo());  // ✅ Correct email
    }

    @Test
    public void purchaseTest() {
        assertTrue(buyer.purchaseProperty(forSaleApartment));
        assertFalse(buyer.purchaseProperty(soldApartment));
    }
}
