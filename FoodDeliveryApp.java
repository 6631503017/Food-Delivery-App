import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Menu.MenuItem;
import Menu.Beverage.BeverageMenuItem;
import Menu.Dessert.DessertMenuItem;
import Menu.Food.FoodMenuItem;

public class FoodDeliveryApp {
    
    private static AppState currentState;

    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static ArrayList<SelectedMenuItem> basket = new ArrayList<>();

    private static Restaurant TastyBites;
    private static Restaurant SpicyNHeat;
    private static Restaurant SweetSecret;
    private static Restaurant IBrew;
    private static Restaurant SalmonHouse;
    private static Restaurant ManeeNoodle;

    private static void AppSetUp() {
        // TastyBites setup
        ArrayList<MenuItem> tastyBitesMenu = new ArrayList<>();
        tastyBitesMenu.add(new FoodMenuItem("Pizza", "Classic Margherita Pizza", 99, FoodMenuItem.Nation.WESTERN));
        tastyBitesMenu.add(new FoodMenuItem("Hamburger", "Juicy Beef Burger", 69, FoodMenuItem.Nation.WESTERN));
        tastyBitesMenu.add(new FoodMenuItem("Salad", "Caesar Salad", 59, FoodMenuItem.Nation.NONE));
        TastyBites = new Restaurant("TastyBites Burger & Pizza Shack", tastyBitesMenu);

        // SpicyNHeat setup
        ArrayList<MenuItem> spicyNHeatMenu = new ArrayList<>();
        spicyNHeatMenu.add(new FoodMenuItem("Yum Laab", "Spicy Thai Salad", 79, FoodMenuItem.Nation.THAI));
        spicyNHeatMenu.add(new FoodMenuItem("Tom Yam Kung", "Spicy Thai Hot and Soup", 129, FoodMenuItem.Nation.THAI));
        spicyNHeatMenu.add(new FoodMenuItem("Kimchi Jjigae", "Spicy korea Soup", 149, FoodMenuItem.Nation.KOREA));
        SpicyNHeat = new Restaurant("Spicy N Heat Isan Sabb", spicyNHeatMenu);

        // SweetSecret setup
        ArrayList<MenuItem> sweetSecretMenu = new ArrayList<>();
        sweetSecretMenu.add(new DessertMenuItem("Cake", "Chocolate Cake", 69, DessertMenuItem.DessertType.CAKE));
        sweetSecretMenu.add(new DessertMenuItem("Donut", "Glazed Donut", 19, DessertMenuItem.DessertType.DONUT));
        sweetSecretMenu.add(new DessertMenuItem("Ice cream", "Vanila Ice cream",49, DessertMenuItem.DessertType.ICE_CREAM));
        SweetSecret = new Restaurant("SweetSecret Harmony Dessert", sweetSecretMenu);

        // IBrew setup
        ArrayList<MenuItem> iBrewMenu = new ArrayList<>();
        iBrewMenu.add(new BeverageMenuItem("Coffee Espresso", "Espresso", 50, BeverageMenuItem.BeverageType.HOT));
        iBrewMenu.add(new BeverageMenuItem("Coffee Latte ", "Latte", 50, BeverageMenuItem.BeverageType.HOT));
        iBrewMenu.add(new BeverageMenuItem("Pepsi ", "pepsi", 20, BeverageMenuItem.BeverageType.ICED));
        IBrew = new Restaurant("IBrew Coffee", iBrewMenu);

        // SalmonHouse setup
        ArrayList<MenuItem> salmonHouseMenu = new ArrayList<>();
        salmonHouseMenu.add(new FoodMenuItem("Salmon Rice", "Grilled Salmon with Rice", 129, FoodMenuItem.Nation.JAPANESE));
        SalmonHouse = new Restaurant("Salmon House", salmonHouseMenu);

        // NoodleHouse setup
        ArrayList<MenuItem> ManeeNoodleMenu = new ArrayList<>();
        ManeeNoodleMenu.add(new FoodMenuItem("Noodle", "Thai style Noodle", 60, FoodMenuItem.Nation.THAI));
        ManeeNoodleMenu.add(new FoodMenuItem("Korea Noodle", "Korea style Noodle", 60, FoodMenuItem.Nation.KOREA));
        ManeeNoodleMenu.add(new FoodMenuItem("Western Noodle", "Western style Noodle", 60, FoodMenuItem.Nation.WESTERN));
        ManeeNoodle = new Restaurant("Manee Me Noodle", ManeeNoodleMenu);

        restaurants.add(TastyBites);
        restaurants.add(SpicyNHeat);
        restaurants.add(SweetSecret);
        restaurants.add(IBrew);
        restaurants.add(SalmonHouse);
        restaurants.add(ManeeNoodle);
    }

    private static void createReceipt(ArrayList<SelectedMenuItem> items) {
        String folderName = "receipt";
        String defaultFileName = "receipt_";

        try {

            Path directoryPath = Paths.get(folderName);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String fileName = defaultFileName;
            int counter = 1;
            long fileCount = Files.list(directoryPath).count();
            
            if (fileCount >= 0) {
                counter += fileCount;
                fileName = defaultFileName + counter + ".txt";
            }
            
            try (BufferedWriter receipt = new BufferedWriter(new FileWriter(Paths.get(folderName, fileName).toString()))) {
                receipt.write("Order Receipt\n\n");

                Map<Restaurant, List<SelectedMenuItem>> itemsByRestaurant = new HashMap<>();
                for (SelectedMenuItem item : items) {
                    itemsByRestaurant.computeIfAbsent(item.getRestaurant(), k -> new ArrayList<>()).add(item);
                }

                for (Map.Entry<Restaurant, List<SelectedMenuItem>> entry : itemsByRestaurant.entrySet()) {
                    Restaurant restaurant = entry.getKey();
                    List<SelectedMenuItem> restaurantItems = entry.getValue();

                    receipt.write("Restaurant: " + restaurant.getName() + "\n");

                    int restaurantTotalPrice = 0;
                    for (SelectedMenuItem item : restaurantItems) {
                        MenuItem menuItem = item.getMenuItem();
                        int amount = item.getAmount();
                        int totalPrice = item.getTotalPrice();
                        restaurantTotalPrice += totalPrice;

                        receipt.write("- " + menuItem.getName() + " (x" + amount + ") - " + totalPrice + "฿\n");
                    }
                    receipt.write("Total: " + restaurantTotalPrice + "฿\n\n");
                }

                int overallPrice = items.stream().mapToInt(SelectedMenuItem::getTotalPrice).sum();
                receipt.write("Overall Price: " + overallPrice + "฿\n\n");

            } catch (IOException e) {
                System.out.println("An error occurred while creating the receipt.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the directory.");
            e.printStackTrace();
        }
    }
    
    public static enum AppState {
        MainMenu,
        DisplayRestuarant,
        CheckOut,
        MenuDetails,
        Exit
    }
    
    public static void main(String[] args) {

        //? Set up the restaurants
        AppSetUp();

        currentState = AppState.MainMenu;
        
        try {

            while (currentState != AppState.Exit)
            {
                switch (currentState)
                {
                    case MainMenu:
                        MainPage();
                        break;
                    
                    case DisplayRestuarant:
                        DisplayRestuarantPage();
                        break;
                        
                    case CheckOut:
                        CheckOutPage();
                        break;
                    
                    case MenuDetails:
                        DisplayRestuarantPreview();
                        break;

                    case Exit:
                        break;

                    default:
                        break;
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Error occur : Invalid input! Please try again.");
        }
    }
    
    

    public static void MainPage() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Food Delivery App");
        System.out.println("1. Show Avaiable Restaurant");
        System.out.println("2. Show all Menu Details");
        System.out.print("Select : ");
        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {
            case 1:
                currentState = AppState.DisplayRestuarant;
                break;

            case 2:
                currentState = AppState.MenuDetails;
                break;

            case 3:
                currentState = AppState.Exit;
                break;
        }
        System.out.println();

    }
    
    public static void DisplayRestuarantPage() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Available Restaurants:");
            for (int i = 0; i < restaurants.size(); i++) {
                System.out.println((i + 1) + ". " + restaurants.get(i).getName());
            }
            System.out.println(restaurants.size()+1 + ". Checkout $");
            System.out.println("0. Back to Main Menu");

            System.out.print("\nPlease select a restaurant: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 0 && choice <= restaurants.size()+1) {

                if (choice == 0) {
                    currentState = AppState.MainMenu;
                    return;
                }

                if (choice == restaurants.size() + 1) {
                    currentState = AppState.CheckOut;
                    return;
                }

                //? Display selected restaurant menu
                Restaurant selectedRestaurant = restaurants.get(choice - 1);
                System.out.println("\nYou have selected: " + selectedRestaurant.getName());
                
                boolean isOrdering = true;
                while (isOrdering) {
                    selectedRestaurant.showMenus();

                    //? Select Menu Item
                    System.out.print("\nPlease select a menu item (0 to finish ordering): ");
                    int menuChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (menuChoice == 0) {
                        isOrdering = false;
                        break;
                    }

                    if (menuChoice < 1 || menuChoice > selectedRestaurant.getMenus().size()) {
                        System.out.println("Invalid menu selection. Please try again.");
                        continue;
                    }

                    //? Enter Amount
                    System.out.print("Please enter the amount: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine();

                    
                    //? Display Menu and Price
                    MenuItem selectedItem = selectedRestaurant.getMenus().get(menuChoice - 1);
                    int totalPrice = selectedItem.getPrice() * amount;
                    System.out.printf("\nTotal Price: %d฿\n", totalPrice);

                    basket.add(new SelectedMenuItem(selectedRestaurant, selectedItem, amount, totalPrice));
                    System.out.println("Item added to basket.");

                    do {
                        System.out.print("\nContinue ordering from this restaurant? (y/n): ");
                        String continueOrder = scanner.nextLine();
                        if (continueOrder.equalsIgnoreCase("y")) {
                            break;
                        } else if (continueOrder.equalsIgnoreCase("n")) {
                            isOrdering = false;
                        }else {
                            System.out.println("Invalid input, please try again.");
                            continue;
                        }
                    } while(isOrdering);
                    
                }

            }  else {
                System.out.println("Invalid input, please try again.");
            }
    }

    public static void CheckOutPage() {
        if (basket.isEmpty()) {
            System.out.println("Your basket is empty.");
            currentState = AppState.DisplayRestuarant;
            return;
        }

        Map<Restaurant, List<SelectedMenuItem>> itemsByRestaurant = new HashMap<>();
        for (SelectedMenuItem item : basket) {
            Restaurant restaurant = item.getRestaurant();
            itemsByRestaurant.computeIfAbsent(restaurant, k -> new ArrayList<>()).add(item);
        }


        System.out.println("\n[Basket Items]");
        for (Map.Entry<Restaurant, List<SelectedMenuItem>> entry : itemsByRestaurant.entrySet()) {
            Restaurant restaurant = entry.getKey();
            List<SelectedMenuItem> items = entry.getValue();

            System.out.println("\nRestaurant: " + restaurant.getName());
            int restaurantTotalPrice = 0;
            for (SelectedMenuItem item : items) {
                MenuItem menuItem = item.getMenuItem();
                int amount = item.getAmount();
                int totalPrice = item.getTotalPrice();
                restaurantTotalPrice += totalPrice;

                System.out.printf("- %s (x%d) - %d฿\n", menuItem.getName(), amount, totalPrice);
            }
            System.out.printf("Total Price : %d฿\n", restaurantTotalPrice);
        }


        int overallPrice = basket.stream().mapToInt(SelectedMenuItem::getTotalPrice).sum();
        System.out.printf("\nOverall Price: %d฿\n", overallPrice);


        Scanner scanner = new Scanner(System.in);
        boolean isCheckout = false;

        do {
            System.out.print("\nWould you like to proceed to checkout? (y/n): ");
            String proceed = scanner.nextLine().trim().toLowerCase();
        
            if (proceed.equals("y")) {
                isCheckout = true;
                currentState = AppState.MainMenu;

                System.out.println("Payment successful. Thank you for your order!");
                System.out.println("Rider will quickly deliver your food to you.\n");
        
                System.out.print("Would you like a receipt? (y/n): ");
                String proceedReceipt = scanner.nextLine().trim().toLowerCase();
        
                if (proceedReceipt.equals("y")) {
                    createReceipt(basket);
                    System.out.println("Receipt created successfully!");
                } else {
                    System.out.println("Thank you for your purchase!");
                }
                
                System.out.println();
                basket.clear();
            } else if (!proceed.equals("n")) {
                System.out.println("Invalid input. Please type 'y' or 'n'.");
            } else {
                System.out.println("Order cancelled.");
            }
        } while (!isCheckout);

    }

    public static void DisplayRestuarantPreview() {
        for (Restaurant restaurant : restaurants) {
            restaurant.showMenuPreview();
            System.out.println(); // Add a blank line for better readability
        }

        currentState = AppState.MainMenu;
    }

    static class SelectedMenuItem {
        private Restaurant restaurant;
        private MenuItem menuItem;
        private int amount;
        private int totalPrice;

        public SelectedMenuItem(Restaurant restaurant, MenuItem menuItem, int amount, int totalPrice) {
            this.restaurant = restaurant;
            this.menuItem = menuItem;
            this.amount = amount;
            this.totalPrice = totalPrice;
        }

        public Restaurant getRestaurant() {
            return restaurant;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getAmount() {
            return amount;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        
    }
}