package Menu.Dessert;

import Menu.*;

public class DessertMenuItem extends MenuItem{

    public enum DessertType {
        ICE_CREAM,
        DONUT,
        CAKE
    }

    private DessertType dessertType;

    public DessertMenuItem(String name, String description, int price, DessertType dessertType) {
        super(name, description, price);

        this.dessertType = dessertType;
    }

    public DessertType getDessertType() {
        return dessertType;
    }

    @Override
    public void printDetails() {
        System.out.println("Dessert Menu Details:");
        super.printDetails();
    }
 
}