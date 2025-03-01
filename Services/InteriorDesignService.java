package Services;

import BasicClasses.Apartment;

public class InteriorDesignService extends ApartmentDecorator {
    private double cost = 2000;
    public InteriorDesignService(Apartment decoratedApartment) {
        super(decoratedApartment);
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public String getServiceName() {
        return "Interior Design Service has been added.\nAdditional cost:"+getCost()+"$";
    }
}
