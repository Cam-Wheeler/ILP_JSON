package dev.cameron.logistics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cameron.logistics.dto.DriverNotificationsDTO;

import java.util.ArrayList;
import java.util.List;

// Handler for the complex nested JSON that requires processing etc (described in the RestController).
public class Endpoint2Handler {

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * Here, we look to utilize the logic implemented in 'collectDriverInfo' in order to create a JsonNode tree,
     * drill into a specific part and perform some processing on a small element as an example of how we can use
     * ObjectMappers and more complex operations to control and manipulate the deserialization of a JSON string
     * into our program.
     * @param json - The original JSON array from the request body.
     * @return A list of DriverNotificationDTO objects.
     * @throws JsonProcessingException
     */
    public static List<DriverNotificationsDTO> deseralize(String json) throws JsonProcessingException {
        ObjectMapper mapper = createMapper();
        JsonNode rootNode = mapper.readTree(json);
        return parseDriverInfo(rootNode, mapper);
    }

    /**
     * This method takes in the JsonNode root and attempts to construct a list of DriverNotificationDTOs
     * We look to automatically convert as much as we can using the ObjectMapper, but we need to do some extra
     * manual processing to count the number of parcels in the truck. This is an example of having to drill down into
     * the JSON tree structure to perform some processing in order to get our data how we need it.
     * @param rootNode - The root of the JsonNode tree.
     * @param mapper - The ObjectMapper that handles the majority of deserialization.
     * @return A list of driver notifications.
     */
    public static List<DriverNotificationsDTO> parseDriverInfo(JsonNode rootNode, ObjectMapper mapper)
            throws JsonProcessingException {

        List<DriverNotificationsDTO> driverNotifications = new ArrayList<>();
        JsonNode fleetArray = rootNode.get("fleet");
        for (JsonNode truck : fleetArray) {
            // Let's try to automatically handle as much as we can.
            DriverNotificationsDTO singleNotification = mapper.treeToValue(truck.get("driver"), DriverNotificationsDTO.class);

            // Let's collect the truck status
            String truckStatus = truck.get("status").asText();
            singleNotification.setTruckStatus(truckStatus);

            // Iterate through all the deliveries in the truck, iterate again through parcels in each delivery and sum together!
            int parcelCount = 0;
            for (JsonNode delivery : truck.get("scheduledDeliveries")) {
                for (JsonNode parcel : delivery.get("items")) {
                    parcelCount = parcelCount + parcel.get("quantity").asInt();
                }
            }
            singleNotification.setNumOfPackages(parcelCount);
            driverNotifications.add(singleNotification);
        }

        return driverNotifications;
    }
}