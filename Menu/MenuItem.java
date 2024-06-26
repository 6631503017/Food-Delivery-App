package Menu;
/**
 * Represent menu
 */
public class MenuItem {
    
    
    protected String name;
    protected String description;
    protected int price;

    public MenuItem(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public void printDetails() {
        System.out.println("Name: " + name);
        System.out.println(" Description: " + description);
    }

}