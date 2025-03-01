package BasicClasses;

import ObserverPattern.Observer;
import SearchStrategy.SearchStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class Broker extends User implements Observer {
    private final Set<Apartment> propertiesAddresses;
    private final List<Apartment> properties;

    public Broker(String name, String contactInfo) {
        super(name, contactInfo);
        this.propertiesAddresses = new HashSet<>();
        this.properties = new ArrayList<>();
    }

    public boolean addProperty(Apartment apartment) {
        if (apartment == null) {
            throw new IllegalArgumentException("Property cannot be null.");
        }

        if (this.propertiesAddresses.contains(apartment)) {
            return false;
        }
        this.propertiesAddresses.add(apartment);
        this.properties.add(apartment);
        return true;
    }

    public List<Apartment> search(SearchStrategy strategy) {
        return strategy.search(properties);
    }

    public boolean removeProperty(List<Integer> address) {
        boolean isRemoved = properties.removeIf(apt -> apt.getAddress().equals(address));
        if (isRemoved) {
            propertiesAddresses.removeIf(apt -> apt.getAddress().equals(address));
        }
        return isRemoved;
    }

    public List<Apartment> getAvailableProperties() {
        return properties.stream().filter(apt -> !apt.isSold()).collect(Collectors.toList());
    }

    public boolean propertyExists(Apartment apartment) {
        return propertiesAddresses.contains(apartment);
    }

    @Override
    public void update(String message) {
        System.out.println(message);
    }

    public List<Apartment> getAllProperties() {
        return new ArrayList<>(properties);
    }


    public Apartment getPropertyByAddress(String addressKey) {
        return properties.stream()
                .filter(apartment -> apartment.getAddress().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining("-"))
                        .equals(addressKey))
                .findFirst()
                .orElse(null);
    }


}
