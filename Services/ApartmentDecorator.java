package Services;

import BasicClasses.Apartment;

public abstract class ApartmentDecorator extends Apartment implements TransactionService{
    protected Apartment  decoratedApartment;
    public ApartmentDecorator(Apartment decoratedApartment){
        super(decoratedApartment.getAvenue(),
                 decoratedApartment.getAddNum()
                ,decoratedApartment.getPricePerSqM()
                ,decoratedApartment.getSize(),
                 decoratedApartment.isSold());
        this.decoratedApartment = decoratedApartment;
    }

    public double calculateTotalPrice(){
        return decoratedApartment.calculateTotalPrice() + getCost();
    }
}
