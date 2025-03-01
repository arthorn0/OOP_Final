package Factories;

import BasicClasses.Broker;
import BasicClasses.Buyer;
import BasicClasses.Seller;
import BasicClasses.User;
import Exceptions.UserException;

public class UserFactory {
    public static User createUser(User user){
        return switch (user) {
            case Broker broker -> new Broker(user.getName(), user.getContactInfo());
            case Seller seller -> new Seller(user.getName(), user.getContactInfo());
            case Buyer buyer -> new Buyer(user.getName(), user.getContactInfo());
            case null, default -> throw new UserException(" User type not recognized: " + user);
        };
    }
}
