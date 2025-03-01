package Services;

import BasicClasses.Apartment;

public class MovingService extends ApartmentDecorator {
    private double cost = 1000;

    public MovingService(Apartment decoratedApartment) {
        super(decoratedApartment);
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public String getServiceName() {
        return "Moving Service Has been added.\nAdditional cost:"+getCost()+"$" ;
    }
}
