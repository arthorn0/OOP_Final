package Loading_And_Updating;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyUpdater {
    private static PropertyUpdater instance;

    private PropertyUpdater() {
    }

    public static PropertyUpdater getInstance() {
        if (instance == null) {
            instance = new PropertyUpdater();
        }
        return instance;
    }

    public void updatePropertyStatus(List<Integer> addressesToUpdate, String filePath) {
        List<String> updatedLines = new ArrayList<>();
        boolean propertyFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            updatedLines.add(reader.readLine());
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 4) {
                    System.out.println("Skipping invalid lines");
                    continue;
                }
                List<Integer> propertyAddress = Arrays.stream(data[0].split("-"))
                        .map(Integer::parseInt)
                        .toList();

                if (propertyAddress.subList(0,2).equals(addressesToUpdate)) {
                    data[3] = "Sold";
                    propertyFound = true;
                }
                updatedLines.add(String.join(",", data));


            }
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
            return;
        }
        if (!propertyFound){
            System.out.println("⚠️ Property not found!");
            return;
        }


        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (String updateLine: updatedLines){
                writer.write(updateLine);
                writer.newLine();
            }
            System.out.println("✅ Property updated successfully!");
        } catch (IOException e){
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }
}


