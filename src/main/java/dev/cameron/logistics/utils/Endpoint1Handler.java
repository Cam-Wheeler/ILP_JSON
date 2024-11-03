package dev.cameron.logistics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cameron.logistics.dto.PaymentLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Handler for the dynamic JSON from different payment methods.
// This is a simple one. As we just need to configure our ObjectMapper to simply ignore any elements that are not
// present in the POJO itself and simply try to deserialize from there.
public class Endpoint1Handler {

    private static final Logger logger = LoggerFactory.getLogger(Endpoint1Handler.class);

    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static PaymentLogDTO deserialize(String json) throws JsonProcessingException {
        ObjectMapper mapper = createObjectMapper();
        PaymentLogDTO paymentLog = mapper.readValue(json, PaymentLogDTO.class);
        logger.info("Payment has been received and logged {}", paymentLog.toString());
        return paymentLog;
    }
}