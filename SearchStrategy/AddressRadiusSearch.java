package SearchStrategy;

import BasicClasses.Apartment;

import java.util.List;
import java.util.stream.Collectors;

public class AddressRadiusSearch implements SearchStrategy {
    private final List<Integer> baseAddress;
    private final int radius;

    public AddressRadiusSearch(List<Integer> baseAddress, int radius) {
        this.baseAddress = baseAddress;
        this.radius = radius;
    }

    @Override
    public List<Apartment> search(List<Apartment> properties) {
        int baseStreet = baseAddress.get(0);
        int baseAvenue = baseAddress.get(1);

        return properties.stream()
                .filter(apt -> {
                    List<Integer> addr = apt.getAddress();
                    int street = addr.get(0);
                    int avenue = addr.get(1);
                    return Math.sqrt(Math.pow(street - baseStreet, 2) + Math.pow(avenue - baseAvenue, 2)) <= radius;
                })
                .collect(Collectors.toList());
    }
}
