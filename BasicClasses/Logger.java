package BasicClasses;

import java.io.*;
import java.util.HashMap;

public class Logger {
    private static final String CREDENTIALS_FILE = "resources/user_credentials.txt";
    private static final int ESSENTIAL_AMOUNT = 6;
    private final HashMap<String, String> buyerMemory;
    private static Logger instance;
    private String currentUser;

    private Logger() {
        this.buyerMemory = new HashMap<>();
        loadCredentials(); // Load saved users at startup
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        if (buyerMemory.containsKey(username) && buyerMemory.get(username).equals(password)) {
            currentUser = username;
            return true;
        }
        return false;
    }

    public void register(String username, String password, String confirm) {
        if (buyerMemory.containsKey(username)) {
            System.out.println("❌ Username already taken.");
            return;
        }

        if (password.length() < ESSENTIAL_AMOUNT) {
            System.out.println("❌ Password too short.");
            return;
        }

        if (!password.equals(confirm)) {
            System.out.println("❌ Passwords do not match.");
            return;
        }

        buyerMemory.put(username, password);
        saveCredentials(); // Save to file
        System.out.println("✅ Registration successful!");
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    private void saveCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            for (var entry : buyerMemory.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving credentials.");
        }
    }

    private void loadCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    buyerMemory.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading credentials.");
        }
    }
}
