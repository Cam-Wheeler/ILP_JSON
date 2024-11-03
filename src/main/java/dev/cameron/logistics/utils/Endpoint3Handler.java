package dev.cameron.logistics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cameron.logistics.dto.CustomerFeedBackDTO;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Handler for the JSONL (Json line) data mocking an OpenAI Batch API in our case.
// The idea here is that we use a buffer to read in lines then crack on with processing the JSON.
public class Endpoint3Handler {

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static List<CustomerFeedBackDTO> deserialize(String jsonlPath) throws IOException {
        ObjectMapper mapper = createMapper();
        List<CustomerFeedBackDTO> feedBackList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonlPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line as a JSON object
                CustomerFeedBackDTO singleLine = processLine(line, mapper);
                feedBackList.add(singleLine);
            }
        }
        return feedBackList;
    }

    public static CustomerFeedBackDTO processLine(String jsonl, ObjectMapper mapper) throws IOException {
        // Treat the line as a tree node we can then parse.
        JsonNode lineNode = mapper.readTree(jsonl);
        // We can easily get the majority of info in the metadata node.
        CustomerFeedBackDTO feedback = mapper.treeToValue(lineNode.get("metadata"), CustomerFeedBackDTO.class);
        // Parse down to get the semantics.
        for (JsonNode message : lineNode.get("choices")) {
            String feedbackSemantics = message.get("message").get("content").asText();
            feedback.setPredictedSemantics(feedbackSemantics);
        }
        return feedback;
    }

    public static String serialize(List<CustomerFeedBackDTO> feedback) throws JsonProcessingException {
        ObjectMapper mapper = createMapper();
        return mapper.writeValueAsString(feedback);
    }
}
