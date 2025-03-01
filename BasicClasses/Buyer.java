package BasicClasses;

import ObserverPattern.Observer;

import java.util.List;

public class Buyer extends User {

    public Buyer(String name, String contactInfo) {
        super(name, contactInfo);
    }

    public boolean purchaseProperty(Apartment apartment) {
        if (apartment.isSold()) {
            return false;
        }
        apartment.setSold(true);
        return true;

    }

}
