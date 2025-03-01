package BasicClasses;

import Exceptions.PropertyAlreadyExitsException;
import Exceptions.PropertyNotAvailableException;
import Exceptions.PropertyNotFoundException;
import java.util.*;



public class Seller extends User {
    private final Set<Broker> brokersSet;
    private final Set<Apartment> ownedProperties;

    public Seller(String name, String contactInfo) {
        super(name, contactInfo);
        this.ownedProperties = new HashSet<>();
        this.brokersSet = new HashSet<>();
    }



    public void listProperty(Apartment property, Broker broker) throws PropertyAlreadyExitsException {
        if (!broker.addProperty(property)) {
            throw new PropertyAlreadyExitsException("Property at " + property.getAddress() + " is already listed.");
        }
        this.ownedProperties.add(property);
        property.settingSeller(this);

    }



    public void removeProperty(List<Integer> address, Broker broker) throws PropertyNotFoundException {
        boolean removedFromSeller = ownedProperties.removeIf(apt -> apt.getAddress().equals(address));
        boolean removedFromBroker = broker.removeProperty(address);

        if (!removedFromSeller && !removedFromBroker) {
            throw new PropertyNotFoundException("Property with address " + address + " not found.");
        }

        System.out.println("Property removed successfully: " + address);

    }

    public List<Apartment> getOwnedProperties() {
        return new ArrayList<>(ownedProperties); // Return a copy to avoid external modification
    }


    public boolean sellProperty(Broker broker, Apartment apartment,Buyer client)  {

        boolean sold = client.purchaseProperty(apartment);
        if (!sold) {
            throw new PropertyNotAvailableException("This apartment is already sold");
        }
        apartment.setOwner(client);
        removeProperty(apartment.getAddress(),broker);
        return true;
    }


    public Set<Broker> getBrokersSet() {
        return this.brokersSet;
    }

    public void updateBrokers(String message){
        for (Broker obs : this.getBrokersSet()){

            obs.update("🔔" + obs.getName() + message);
        }
    }
}
