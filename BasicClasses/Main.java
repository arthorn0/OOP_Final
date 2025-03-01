package BasicClasses;

import Exceptions.PropertyNotFoundException;
import Loading_And_Updating.PropertyLoader;
import Loading_And_Updating.PropertyUpdater;
import SearchStrategy.*;
import Services.*;
import Factories.UserFactory;

import java.io.Console;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String EXIT_COMMAND = "quit";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getInstance();  // ✅ Singleton Logger Instance

            System.out.println("=== 🔑 Welcome to the Real Estate System ===");
        boolean authenticated = false;
        String currentUser = null;

        while (!authenticated) {
            System.out.println("\n1️⃣ Register");
            System.out.println("2️⃣ Login");
            System.out.println("3️⃣ Exit");
            System.out.print("➡️ Enter your choice: ");

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("❌ Invalid input. Enter a number.");
                continue;
            }

            int authChoice = scanner.nextInt();
            scanner.nextLine();

            if (authChoice == 3) {
                System.out.println("👋 Exiting... Thank you for using the Real Estate System!");
                return;
            }

            String username, password, confirmPassword;
            switch (authChoice) {
                case 1 -> {
                    System.out.print("👤 Enter Username: ");
                    username = scanner.nextLine();

                    Console console = System.console();
                    if (console != null) {
                        char[] passwordArray = console.readPassword("🔑 Enter Password: ");
                        password = new String(passwordArray);
                        char[] confirmArray = console.readPassword("🔄 Confirm Password: ");
                        confirmPassword = new String(confirmArray);
                    } else {
                        System.out.print("🔑 Enter Password: ");
                        password = scanner.nextLine();
                        System.out.print("🔄 Confirm Password: ");
                        confirmPassword = scanner.nextLine();
                    }

                    logger.register(username, password, confirmPassword);
                }
                case 2 -> {
                    System.out.print("👤 Enter Username: ");
                    username = scanner.nextLine();

                    Console console = System.console();
                    if (console != null) {
                        char[] passwordArray = console.readPassword("🔑 Enter Password: ");
                        password = new String(passwordArray);
                    } else {
                        System.out.print("🔑 Enter Password: ");
                        password = scanner.nextLine();
                    }

                    if (logger.login(username, password)) {
                        authenticated = true;
                        currentUser = logger.getCurrentUser();
                    } else {
                        System.out.println("❌ Incorrect username or password. Try again.");
                    }
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }

        try {
            Broker broker = (Broker) UserFactory.createUser(new Broker("Jeremy Kane", "jeremy@example.com"));
            Seller seller = (Seller) UserFactory.createUser(new Seller("John Doe", "john@example.com"));
            seller.getBrokersSet().add(broker);
            Buyer buyer = (Buyer) UserFactory.createUser(new Buyer(currentUser, currentUser + "@example.com"));

            String filePath = "resources/properties.csv";
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("❌ Error: Property file not found at: " + filePath);
                return;
            }

            // ✅ Using Singleton for PropertyLoader and PropertyUpdater
            PropertyUpdater updater = PropertyUpdater.getInstance();
            PropertyLoader loader = PropertyLoader.getInstance();
            loader.loadProperties(filePath, broker, seller);

            while (authenticated) {
                System.out.println("\n=== 🏠 Real Estate System (Logged in as: " + currentUser + ") ===");
                System.out.println("1️⃣ View Available Properties");
                System.out.println("2️⃣ Search Properties");
                System.out.println("3️⃣ Purchase a Property");
                System.out.println("4️⃣ Logout & Exit");
                System.out.print("➡️ Enter your choice (or type 'quit' to go back): ");

                if (!scanner.hasNextInt()) {
                    scanner.next();
                    System.out.println("❌ Invalid input. Enter a number.");
                    continue;
                }
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        do {
                            System.out.println("\n=== Available Properties ===");
                            seller.getOwnedProperties().forEach(System.out::println);
                            System.out.print("\n🔙 Type 'quit' to return: ");
                        } while (!scanner.nextLine().equalsIgnoreCase(EXIT_COMMAND));
                    }
                    case 2 -> {
                        while (true) {
                            System.out.println("\n🔍 Choose Search Method:");
                            System.out.println("1. Search by Address Radius");
                            System.out.println("2. Search by Price Range");
                            System.out.println("3. Search by Size Range");
                            System.out.println("4. Search by Availability");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("➡️ Enter your choice: ");

                            if (!scanner.hasNextInt()) {
                                scanner.next();
                                System.out.println("❌ Invalid input. Enter a number.");
                                continue;
                            }
                            int searchChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (searchChoice == 5)
                                break;

                            System.out.print("📍 Enter Avenue: ");
                            int baseAvenue = scanner.nextInt();
                            System.out.print("📍 Enter Street: ");
                            int baseStreet = scanner.nextInt();
                            System.out.print("🎯 Enter Radius: ");
                            int radius = scanner.nextInt();
                            scanner.nextLine();

                            List<Integer> baseAddress = Arrays.asList(baseAvenue, baseStreet);
                            SearchStrategy strategy = null;

                            switch (searchChoice) {
                                case 1 -> strategy = new AddressRadiusSearch(baseAddress, radius);
                                case 2 -> {
                                    System.out.print("💰 Enter min price: ");
                                    double minPrice = scanner.nextDouble();
                                    System.out.print("💰 Enter max price: ");
                                    double maxPrice = scanner.nextDouble();
                                    scanner.nextLine();
                                    strategy = new PriceRangeSearch(minPrice, maxPrice, baseAddress, radius);
                                }
                                case 3 -> {
                                    System.out.print("📐 Enter min size: ");
                                    double minSize = scanner.nextDouble();
                                    System.out.print("📐 Enter max size: ");
                                    double maxSize = scanner.nextDouble();
                                    scanner.nextLine();
                                    strategy = new SizeRangeSearch(minSize, maxSize, baseAddress, radius);
                                }
                                case 4 -> strategy = new AvailabilitySearch(baseAddress, radius);
                                default -> System.out.println("❌ Invalid choice.");
                            }

                            if (strategy != null) {
                                List<Apartment> results = broker.search(strategy);
                                if (results.isEmpty()) {
                                    System.out.println("No matching properties found.");
                                } else {
                                    System.out.println("\n=== Search Results ===");
                                    results.forEach(System.out::println);
                                }
                            }
                        }
                    }
                    case 3 -> {
                        int street;
                        int avenue;
                        while(true) {
                            System.out.print("📍 Enter the Avenue number or type 'quit' to return: ");
                             if (!scanner.hasNextInt()){
                                 System.out.println("Invalid Choice");
                                 continue;
                             }
                             avenue = scanner.nextInt();
                             scanner.nextLine();

                            if (avenue <= 0){
                                System.out.println("Invalid Choice");
                                scanner.next();
                                continue;
                            }

                            System.out.println("📍Enter the Street number or type 'quit' to return: ");
                            if (!scanner.hasNextInt() ){
                                System.out.println("Invalid Choice");
                                scanner.next();
                                continue;
                            }
                            street = scanner.nextInt();
                            scanner.nextLine();

                            if (street <= 0){
                                System.out.println("Invalid Choice");
                                scanner.next();
                                continue;
                            }

                            break;
                        }
                        List<Integer> address = Arrays.asList(avenue, street);
                        Apartment selectedProperty = broker.getAllProperties().stream()
                                .filter(apartment -> apartment.getAddress().equals(address) && !apartment.isSold())
                                .findFirst()
                                .orElse(null);

                        if (selectedProperty == null) {
                            System.out.println("❌ Property not found or already sold.");
                        } else {

                            int serviceChoice;
                            String yesOrNo;

                            while(true) {
                                System.out.println("----Would you like An additional services?----\n[Y/N]");
                                yesOrNo = scanner.next().trim().toUpperCase();

                                if (!yesOrNo.equals("Y") && !yesOrNo.equals("N")){
                                    System.out.println("Invalid Choice");
                                    continue;
                                }

                                if (yesOrNo.equals("N")){
                                    break;
                                }

                                System.out.println("---Available Services---");
                                System.out.println("1.Cleaning Service");
                                System.out.println("2.Evening Service");
                                System.out.println("3.Interior Design Service");
                                System.out.println("4.Moving Service");

                                if (!scanner.hasNextInt()){
                                    System.out.println("Invalid choice");
                                    scanner.next();
                                    continue;
                                }

                                serviceChoice = scanner.nextInt();
                                if (serviceChoice < 1 || serviceChoice > 4) {
                                    System.out.println("❌ Invalid service choice. Try again.");
                                    continue;  // Go back to service selection
                                }
                                selectedProperty = switch (serviceChoice) {
                                    case 1 -> new CleaningService(selectedProperty);
                                    case 2 -> new EveningService(selectedProperty);
                                    case 3 -> new InteriorDesignService(selectedProperty);
                                    case 4 -> new MovingService(selectedProperty);
                                    default -> selectedProperty;
                                };
                                selectedProperty.settingSeller(seller);

                                break;
                            }
                            try {

                                System.out.println("Property Owner is :" + selectedProperty.getOwner().getName()+" The Seller");
                                    seller.sellProperty(broker, selectedProperty, buyer);
                                    updater.updatePropertyStatus(address, filePath);
                                    System.out.println("✅ " + buyer.getName() + " has purchased: " + selectedProperty);
                                    seller.updateBrokers(" Property at " + address + " has been sold.");
                                System.out.println("Property owner is :" +selectedProperty.getOwner().getName()+" The Client");

                            } catch (PropertyNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    case 4 -> {
                        System.out.println("👋 Logging out...");
                        authenticated = false;
                        logger.logout();
                    }
                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Unexpected error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
