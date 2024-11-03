package dev.cameron.logistics.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// A simple mock implementation of a Kafka producer, this will dummy emit
// messages to topics as an example of how taking in complex JSON and distilling it
// can be useful in event based producer/consumer systems.
public class Emitter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void emit(String message) {
        String topic = "KafkaTopic123";
        logger.info("Producing message to {}, content of message is {}",
                topic, message);
    }
}
