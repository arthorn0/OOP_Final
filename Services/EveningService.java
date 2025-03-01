package Services;

import BasicClasses.Apartment;

public class EveningService  extends ApartmentDecorator {
    private final double cost = 800;
    public EveningService(Apartment decoratedApartment) {
        super(decoratedApartment);
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public String getServiceName() {
        return "Evening Service has been added.\nAdditional cost:"+getCost()+"$";
    }
}

