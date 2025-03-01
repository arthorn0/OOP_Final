package SearchStrategy;

import BasicClasses.Apartment;

import java.util.List;

public interface SearchStrategy {
    List<Apartment> search(List<Apartment> apartments);

    default List<Apartment> filterByRadius(List<Apartment> apartments, List<Integer> baseAddress, int radius) {
        return apartments.stream()
                .filter(apartment -> {
                    List<Integer> address = apartment.getAddress();
                    if (address.size() < 2) return false;

                    int avenueDiff = Math.abs(address.get(0) - baseAddress.get(0));
                    int streetDiff = Math.abs(address.get(1) - baseAddress.get(1));
                    int distance = avenueDiff + streetDiff; // Manhattan Distance

                    return distance <= radius;
                })
                .toList();
    }
}
