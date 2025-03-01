package SearchStrategy;

import BasicClasses.Apartment;
import SearchStrategy.SearchStrategy;

import java.util.List;

public class PriceRangeSearch implements SearchStrategy {
    private final double minPrice;
    private final double maxPrice;
    private final List<Integer> baseAddress;
    private final int radius;

    public PriceRangeSearch(double minPrice, double maxPrice, List<Integer> baseAddress, int radius) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.baseAddress = baseAddress;
        this.radius = radius;
    }

    @Override
    public List<Apartment> search(List<Apartment> apartments) {
        List<Apartment> filteredByRadius = filterByRadius(apartments, baseAddress, radius);

        return filteredByRadius.stream()
                .filter(apartment -> apartment.getPricePerSqM() >= minPrice && apartment.getPricePerSqM() <= maxPrice)
                .toList();
    }
}
