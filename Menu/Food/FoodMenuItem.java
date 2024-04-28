package Menu.Food;

import Menu.*;

public class FoodMenuItem extends MenuItem{

    public enum Nation {
        WESTERN,
        THAI,
        JAPANESE,
        KOREA,
        NONE
    }

    private Nation nation;

    public FoodMenuItem(String name, String description, int price) {
        super(name, description, price);
        this.nation = Nation.NONE;
    }

    public FoodMenuItem(String name, String description, int price, Nation nation) {
        super(name, description, price);
        this.nation = nation;
    }

    public Nation getNation() {
        return nation;
    }
}
