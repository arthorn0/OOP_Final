package SearchStrategy;

import BasicClasses.Apartment;

import java.util.List;

public class SizeRangeSearch implements SearchStrategy {
    private final double minSize;
    private final double maxSize;
    private final List<Integer> baseAddress;
    private final int radius;

    public SizeRangeSearch(double minSize, double maxSize, List<Integer> baseAddress, int radius) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.baseAddress = baseAddress;
        this.radius = radius;
    }

    @Override
    public List<Apartment> search(List<Apartment> apartments) {
        List<Apartment> filteredByRadius = filterByRadius(apartments, baseAddress, radius);

        return filteredByRadius.stream()
                .filter(apartment -> apartment.getSize() >= minSize && apartment.getSize() <= maxSize)
                .toList();
    }
}
