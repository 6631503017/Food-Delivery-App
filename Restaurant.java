import java.util.ArrayList;

import Menu.MenuItem;

/**
 * Restaurant class that contains a name and list of dishes in the restaurant.
 */
public class Restaurant {

    private String name;
    private ArrayList<MenuItem> menus;

    public Restaurant(String name, ArrayList<MenuItem> menus) {
        this.name = name;
        this.menus = menus;
    }

    /**
     * Getter method for name.
     * @return name of the restaurant.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for list of menu.
     * @return Arraylist of menu items.
     */
    public ArrayList<MenuItem> getMenus() {
        return menus;
    }
    
    public void showMenus() {

        System.out.println(String.format("< %s >", this.getName()));

        int menuNumber = 1;
        for (MenuItem menu : menus) {
            System.out.println(String.format("%d} %s - %d฿", menuNumber, menu.getName(), menu.getPrice() ));
            menuNumber++;
        }

    }

}