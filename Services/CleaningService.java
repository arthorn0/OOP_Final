package Services;

import BasicClasses.Apartment;

public class CleaningService extends ApartmentDecorator {
    private final double serviceCost = 500;

    public CleaningService(Apartment decoratedApartment) {
        super(decoratedApartment);

    }

    @Override
    public double getCost() {
        return this.serviceCost;
    }


    @Override
    public String getServiceName() {
        return "Cleaning Service has been added.\nAdditional cost:" +getCost()+"$";
    }

}
