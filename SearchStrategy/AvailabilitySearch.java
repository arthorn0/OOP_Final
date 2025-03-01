package SearchStrategy;

import BasicClasses.Apartment;

import java.util.List;

public class AvailabilitySearch implements SearchStrategy {
    private final List<Integer> baseAddress;
    private final int radius;

    public AvailabilitySearch(List<Integer> baseAddress, int radius) {
        this.baseAddress = baseAddress;
        this.radius = radius;
    }

    @Override
    public List<Apartment> search(List<Apartment> apartments) {
        List<Apartment> filteredByRadius = filterByRadius(apartments, baseAddress, radius);
        return filteredByRadius.stream().filter(apartment -> !apartment.isSold()).toList();
    }
}
