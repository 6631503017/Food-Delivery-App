import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Menu.MenuItem;
import Menu.Beverage.BeverageMenuItem;
import Menu.Dessert.DessertMenuItem;
import Menu.Food.FoodMenuItem;

public class FoodDeliveryApp {
    
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();

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
        SpicyNHeat = new Restaurant("Spicy N Heat Isan Sabb", spicyNHeatMenu);

        // SweetSecret setup
        ArrayList<MenuItem> sweetSecretMenu = new ArrayList<>();
        sweetSecretMenu.add(new DessertMenuItem("Cake", "Chocolate Cake", 69, DessertMenuItem.DessertType.CAKE));
        sweetSecretMenu.add(new DessertMenuItem("Donut", "Glazed Donut", 19, DessertMenuItem.DessertType.DONUT));
        SweetSecret = new Restaurant("SweetSecret Harmony Dessert", sweetSecretMenu);

        // IBrew setup
        ArrayList<MenuItem> iBrewMenu = new ArrayList<>();
        iBrewMenu.add(new BeverageMenuItem("Coffee", "Espresso", 50, BeverageMenuItem.BeverageType.HOT));
        IBrew = new Restaurant("IBrew Coffee", iBrewMenu);

        // SalmonHouse setup
        ArrayList<MenuItem> salmonHouseMenu = new ArrayList<>();
        salmonHouseMenu.add(new FoodMenuItem("Salmon Rice", "Grilled Salmon with Rice", 129, FoodMenuItem.Nation.JAPANESE));
        SalmonHouse = new Restaurant("Salmon House", salmonHouseMenu);

        // NoodleHouse setup
        ArrayList<MenuItem> ManeeNoodleMenu = new ArrayList<>();
        ManeeNoodleMenu.add(new FoodMenuItem("Noodle", "Thai style Noodle", 60, FoodMenuItem.Nation.THAI));
        ManeeNoodle = new Restaurant("Manee Me Noodle", ManeeNoodleMenu);

        restaurants.add(TastyBites);
        restaurants.add(SpicyNHeat);
        restaurants.add(SweetSecret);
        restaurants.add(IBrew);
        restaurants.add(SalmonHouse);
        restaurants.add(ManeeNoodle);
    }

    private static void createReceipt(String restaurantName, String menuItem, int price) {
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
                receipt.write("Restaurant: " + restaurantName + "\n");
                receipt.write("Item: " + menuItem + "\n");
                receipt.write("Price: " + price + "฿\n");
                receipt.write("Thank you for your purchase!");

            } catch (IOException e) {
                System.out.println("An error occurred while creating the receipt.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the directory.");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        AppSetUp();

        System.out.println("Welcome to Food Delivery App \n");
        System.out.println("Available Restaurants:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i+1) + ". " + restaurants.get(i).getName());
        }

        System.out.print("\nPlease select a restaurant: ");
        Scanner scanner = new Scanner(System.in);

        try {
            int choice = scanner.nextInt();
            
            if (choice >= 1 && choice <= restaurants.size()) {
                Restaurant selectedRestaurant = restaurants.get(choice - 1);
                System.out.println("\nYou have selected: " + selectedRestaurant.getName());
                selectedRestaurant.showMenus();

                System.out.print("\nPlease select a menu item: ");
                int menuChoice = scanner.nextInt();
                scanner.nextLine();
    
                System.out.print("Please enter the amount: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
    
                MenuItem selectedItem = selectedRestaurant.getMenus().get(menuChoice - 1);
                int totalPrice = selectedItem.getPrice() * amount;
                System.out.printf("\nTotal Price: %d฿\n", totalPrice);
    
                System.out.print("Would you like to proceed to payment? (y/n): ");
                String proceed = scanner.nextLine();
    
                if (proceed.equalsIgnoreCase("y")) {
                    System.out.println("Payment successful. Thank you for your order!");

                    System.out.print("Would you like a receipt? (y/n): ");
                    String proceedReceipot = scanner.nextLine();

                    if (proceedReceipot.equalsIgnoreCase("y")) {
                        createReceipt(selectedRestaurant.getName(), selectedItem.getName(), selectedItem.getPrice());
                        System.out.println("Receipt created successfully!");
                    } else {
                        System.out.println("Thank you for your purchase!");
                    }
                    
                } else {
                    System.out.println("Order cancelled.");
                }

            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid restaurant number.");
        } finally {
            scanner.close();
        }

    }

}