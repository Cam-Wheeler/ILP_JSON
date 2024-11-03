package dev.cameron.logistics.controllers;

// Local Imports
import dev.cameron.logistics.dto.CustomerFeedBackDTO;
import dev.cameron.logistics.dto.DriverNotificationsDTO;
import dev.cameron.logistics.dto.PaymentLogDTO;
import dev.cameron.logistics.services.Emitter;
import dev.cameron.logistics.utils.Endpoint1Handler;
import dev.cameron.logistics.utils.Endpoint2Handler;
import dev.cameron.logistics.utils.Endpoint3Handler;

// Java Imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class SimpleRestController {

    private final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);

    // Simple check to ensure we are up and running.
    @GetMapping("/isAlive")
    public boolean isAlive() { return true; }

    /**
     * JSON can be dynamic in production situations, so we must be able to react accordingly. Consider an example of a
     * payment logger for our logistics hub. We can accept payment in many forms (Apple pay, Google Pay, Debit/Credit,
     * PayPal etc) but each of these payment gateways generate their own JSON with some extra fields in each that
     * we do not need. Performing an automatic conversion with @ResponseBody isn't ideal as our JSON
     * is not static. For this, we need to create a JSON handler able to take what we want from the JSON, and ignore
     * what we do not without errors.
     */
    @PostMapping("/endpoint1")
    public ResponseEntity<String> endpoint1(@RequestBody String body) {
        if (body == null) {
            return new ResponseEntity<String>("Error with request body.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Note we do not check for nulls here for simplicity, in prod, we would need to check for nulls before logging.
            PaymentLogDTO deseralize = Endpoint1Handler.deserialize(body);
            return new ResponseEntity<String>("Successfully logged payment", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<String>("Error when parsing request body.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * When working in production, we can often get complex nested JSON being handed too us. This renders using the
     * automatic serialization impractical as it will contain information we do not want, dealing with large nested POJOs
     * will become a pain and tightly couple parts of our code together, it also means that we must take the JSON data
     * as is without any processing (which we might need).
     *
     * Consider an example in our logistics service where we receive a nested JSON from one of our hubs information
     * systems each day about the current happenings. For this endpoint, we need to create a
     * JSON handler that can parse a complex nested JSON structure, collect what we need, process certain parts into
     * intermediate values and pass them onto other services.
     *
     * Note: we are dummying the passing onto other services here. In real life, we could use Kafka to produce messages
     * to topics that other services can consume.
     */
    @PostMapping("/endpoint2")
    public ResponseEntity<String> endpoint2(@RequestBody String body) {
        if (body == null) {
            return new ResponseEntity<String>("Error with request body." , HttpStatus.BAD_REQUEST);
        }
        List<DriverNotificationsDTO> notificationsToEmit;
        try {
            notificationsToEmit = Endpoint2Handler.deseralize(body);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<String>("Error when parsing request body.", HttpStatus.BAD_REQUEST);
        }
        Emitter emitter = new Emitter();
        if (!notificationsToEmit.isEmpty()) {
            for (DriverNotificationsDTO notification : notificationsToEmit) {
                emitter.emit(notification.toString());
            }
        }
        return new ResponseEntity<String>("Successfully written to topic.", HttpStatus.OK);
    };

    /**
     * Certain APIs use JSONL files, where each line in the file is a JSON object.
     * OpenAI's batch API is one example, they will return your batch request back to you as a large JSONL files.
     * Consider an example in our logistics hub where we are using GPT to perform some semantic analysis on our
     * customer reviews. This endpoint looks to demonstrate how we can process JSON arrays and returns the
     * filtered JSON back to the client.
     *
     * @param pathToJsonl - The path to the local jsonl file (located in the resources folder).
     * @return ResponseEntity object with the filtered JSON in a JSON array.
     */
    @GetMapping("/endpoint3")
    public ResponseEntity<String> endpoint3(@RequestParam String pathToJsonl) {
        try {
            List<CustomerFeedBackDTO> filteredFeedback = Endpoint3Handler.deserialize(pathToJsonl);
            String filterdJson = Endpoint3Handler.serialize(filteredFeedback);
            return new ResponseEntity<String>(filterdJson, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error trying to parse JSONL file.", HttpStatus.BAD_REQUEST);
        }
    }
}
