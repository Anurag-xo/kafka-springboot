````markdown
# Products Microservice with Spring Boot and Kafka

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)

A production-ready microservice for product management with event-driven architecture using Spring Boot and Apache Kafka.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Kafka Configuration](#kafka-configuration)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## Features

✅ Product creation via REST API  
✅ Event publishing to Kafka  
✅ Custom Kafka topic configuration  
✅ Comprehensive error handling  
✅ Async/Sync publishing modes  
✅ Logging with SLF4J

## Tech Stack

- **Framework**: Spring Boot 3.4.5
- **Language**: Java 17
- **Messaging**: Apache Kafka
- **Build Tool**: Maven Wrapper
- **Logging**: SLF4J with Logback

## Prerequisites

- JDK 17+
- Apache Kafka 3.0+
- Maven 3.6+ (or use provided wrapper)

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/anurag-xo-kafka-springboot.git
cd anurag-xo-kafka-springboot
```
````

### 2. Start Kafka

```bash
# Start Zookeeper (in separate terminal)
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka (in separate terminal)
bin/kafka-server-start.sh config/server.properties
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

### 4. Test the service

```bash
./curl_http_post.sh
# Or manually:
curl -X POST http://localhost:38079/products \
     -H "Content-Type: application/json" \
     -d '{"title":"iphone","price":800, "quantity":5}'
```

## API Documentation

### POST /products

Create a new product and publish event to Kafka

**Request Body**:

```json
{
  "title": "string",
  "price": number,
  "quantity": integer
}
```

**Success Response**:

```
HTTP 201 Created
"3fa85f64-5717-4562-b3fc-2c963f66afa6" (productId)
```

**Error Responses**:

- `400 Bad Request` - Invalid input
- `500 Internal Server Error` - Kafka/processing error

## Kafka Configuration

The service automatically creates a topic with:

```java
TopicBuilder.name("products-created-events-topic")
    .partitions(3)
    .replicas(3)
    .configs(Map.of("min.insync.replicas","2"))
    .build();
```

**Event Schema**:

```java
public class ProductCreatedEvent {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    // getters/setters
}
```

## Development

### Build

```bash
./mvnw clean package
```

### Test

```bash
./mvnw test
```

### Code Style

- Follow Google Java Style Guide
- 4-space indentation
- K&R braces style

### Future Improvements

- [ ] Add database persistence
- [ ] Implement proper validation
- [ ] Add Swagger documentation
- [ ] Implement JWT security

## Contributing

Pull requests are welcome! Please:

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a pull request

## License

This project is licensed under the [Apache License 2.0](LICENSE).

---

**Maintained by**: [Anurag-xp] | anuragrk04@gmail.com

```

This README includes:
1. GitHub badges for key technologies
2. Table of contents with anchor links
3. Clear feature list with emoji bullets
4. Detailed tech stack information
5. Step-by-step quick start guide
6. Complete API documentation
7. Kafka configuration details
8. Development guidelines
9. Contribution instructions
10. License information

The formatting uses:
- Proper Markdown syntax for GitHub rendering
- Code blocks with syntax highlighting
- Emojis for better visual scanning
- Clear section headers
- Consistent spacing and structure

Would you like me to add any additional sections like deployment instructions, monitoring setup, or environment variables configuration?
```
