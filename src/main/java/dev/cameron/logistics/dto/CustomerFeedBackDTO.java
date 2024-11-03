package dev.cameron.logistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// DTO for customer feedback. Used when processing JSONL files from GPT Batch API.
public class CustomerFeedBackDTO {
    @JsonProperty("review_text")
    private String feedback;
    @JsonProperty("confidence_score")
    private float predictionConfidence;
    @JsonProperty("hub_location")
    private String storeLocation;
    private String predictedSemantics;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPredictedSemantics() {
        return predictedSemantics;
    }

    public void setPredictedSemantics(String predictedSemantics) {
        this.predictedSemantics = predictedSemantics;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    @Override
    public String toString() {
        return "CustomerFeedBackDTO{" +
                "feedback='" + feedback + '\'' +
                ", predictedSemantics='" + predictedSemantics + '\'' +
                ", storeLocation='" + storeLocation + '\'' +
                '}';
    }
}
