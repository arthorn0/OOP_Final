package Loading_And_Updating;

import BasicClasses.Apartment;
import BasicClasses.Broker;
import BasicClasses.Seller;
import Factories.ApartmentFactory;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PropertyLoader {
    private static PropertyLoader instance;

    private PropertyLoader() {}

    public static PropertyLoader getInstance() {
        if (instance == null) {

            instance = new PropertyLoader();

        }
        return instance;
    }

    public void loadProperties(String filePath, Broker broker, Seller seller) throws IOException {
        Map<String, String> apartmentMap = new HashMap<>();
        Set<String> asset = new HashSet<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // Skip header line

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.trim().split(",");

                if (data.length != 4) {
                    continue;
                }

                List<Integer> addressList = Arrays.stream(data[0].split("-"))
                        .map(Integer::parseInt)
                        .toList();


                int size = data[1].equals("null") ? 0 : Integer.parseInt(data[1]);
                double pricePerSquareMeter = data[2].equals("null") ? 0 : Double.parseDouble(data[2]);

                String address = addressList.get(0) + "-" + addressList.get(1);
                if (!asset.add(address)) {
                    continue;
                }

                Apartment apartment = ApartmentFactory.createApartment(addressList, pricePerSquareMeter, size);

                seller.listProperty(apartment, broker);

                String parentKey = addressList.size() > 2
                        ? addressList.subList(0, 2).stream().map(String::valueOf).collect(Collectors.joining("-"))
                        : data[0];

                String subApt = addressList.size() > 2
                        ? addressList.subList(2, addressList.size()).stream().map(String::valueOf).collect(Collectors.joining(""))
                        : "Null";

                apartmentMap.put(parentKey, subApt);

                if (addressList.size() > 2) {
                    Apartment parentApartment = broker.getPropertyByAddress(parentKey);
                    if (parentApartment != null) {
                        List<Integer> subApartmentAddress = new ArrayList<>(addressList);
                        parentApartment.getFullAddress().addAll(addressList);

                        for (char num : subApt.toCharArray()) {
                            int subNum = Character.getNumericValue(num);
                            for (int i = 0; i < subNum; i++) {
                                Apartment sub = ApartmentFactory.createApartment(subApartmentAddress, pricePerSquareMeter, size);
                                if (!parentApartment.getSubApartments().contains(sub)){
                                parentApartment.addSubApartment(sub);
                                }
                                subApartmentAddress.set(subApartmentAddress.size() - 1, subApartmentAddress.getLast());
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            System.err.println(" Error loading properties: " + e.getMessage());
        }
    }
}



