package Menu.Beverage;

import Menu.*;

public class BeverageMenuItem extends MenuItem {

    public enum BeverageType {
        HOT,
        ICED,
        FRAPPE
    }

    private BeverageType beverageType;

    public BeverageMenuItem(String name, String description, int price , BeverageType beverageType) {
        super(name, description, price);

        this.beverageType = beverageType;
    }

    public BeverageType getBeverageType() {
        return beverageType;
    }

    @Override
    public void printDetails() {
        System.out.println("Beverage Menu Item Details:");
        super.printDetails();
    }

}
