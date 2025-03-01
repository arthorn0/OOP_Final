package Factories;

import BasicClasses.Apartment;
import java.util.List;

public class ApartmentFactory {
    public static Apartment createApartment(List<Integer> addressList, double pricePerSqM, double size) {
        if (addressList == null || addressList.size() < 2) {
            throw new IllegalArgumentException("Address list must contain at least an avenue and a street.");
        }

        int avenue = addressList.get(0);
        int street = addressList.get(1);

        return new Apartment(avenue, street, pricePerSqM, size, false);
    }




}
