import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class RestaurantManager {
    
    private static Gson gson = new Gson();

    public static void exportRestaurantData(ArrayList<Restaurant> restaurants) {
        String folderName = "Export";
        String FileName = "export.json";

        try {

            Path directoryPath = Paths.get(folderName);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
        
            try (BufferedWriter export = new BufferedWriter(new FileWriter(Paths.get(folderName, FileName).toString()))) {
                String jsonString = gson.toJson(restaurants);
                export.write(jsonString);
                System.out.println("Restaurant Exported!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the directory.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Restaurant> importRestaurantData() {
        String folderName = "Import";
        String FileName = "export.json";

        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(folderName, FileName).toString()))) {
            ArrayList<Restaurant> restaurants = gson.fromJson(reader, new TypeToken<ArrayList>(){}.getType());
            System.out.println("Restaurant Imported!");
            return restaurants;

        } catch (IOException e) {
            System.out.println("Error importing restaurant data from JSON: " + e.getMessage());
            return null;
        }
    }
}
