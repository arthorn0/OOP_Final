package BasicClasses;

import java.io.Console;
import java.util.HashSet;
import java.util.Set;

public abstract class User {
    private final String name;
    private final String contactInfo;
    private final Set<Apartment> ownedApartment;



    public User(String name,String contactInfo){
        this.name = name;
        this.contactInfo = contactInfo;
        this.ownedApartment = new HashSet<>();
    }


    public String getName(){
        return this.name;
    }
    public String getContactInfo(){
        return this.contactInfo;
    }

    public Set<Apartment> getOwnedApartment() {
        return ownedApartment;
    }

}

