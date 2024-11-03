# Bonus JSON Work

This repo explores various approaches for handling JSON in Spring applications. The REST application is based on a logistics microservice, featuring three endpoints:

Endpoint 1

This endpoint is designed to handle payment logging. The payment system accepts various payment methods, such as Apple/Google Pay, Debit cards, and PayPal. Each payment type produces a slightly different JSON result that is passed to our service. We need to extract the essential information for logging purposes while ignoring the additional details that each payment type provides.

Endpoint 2

Production JSON is often complex and nested. This endpoint demonstrates how we can use JsonNode to parse nested JSON like a tree structure. Additionally, it shows that we can process JSON during deserialization. In this example, we receive JSON from a service tracking logistics hub activities across the UK. To assist delivery drivers, a separate Driver Notification microservice has been created (not really but just imagine). Endpoint2 parses nested JSON to generate a Driver Notification DTO, which can be consumed by the Driver Notification service.

Endpoint 3

As part of our logistics operations, we receive a significant amount of customer feedback. To analyze this feedback efficiently, we’re leveraging GPT to determine the tone. This endpoint uses a mock JSONL file (representative of what OpenAI’s Batch API returns) to parse multiple JSON objects and filter results. The filtered results are returned as a JSON array to the client. For this endpoint, add the JSONL file path within your system as a query parameter.

The most important directories to look in are:
- controller directory: Contains the RestController.
- utils directory: Houses each of the JSON handlers.
- dto directory: Contains the data transfer objects (DTOs).
- service directory: Includes a basic Kafka producer mock that logs messages.
- resource directory: Includes example JSON values for API requests. For Endpoint 3, remember to specify the path to the JSONL file in the query parameter.