package com.notrlyanurag.ws.products;

import com.notrlyanurag.ws.core.ProductCreatedEvent;
import java.util.UUID;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public String createProduct(CreateProductRestModel ProductRestModel) throws Exception {

    String productId = UUID.randomUUID().toString();

    // TODO: Presist Product details into database table before publishing an Event.

    ProductCreatedEvent productCreatedEvent =
        new ProductCreatedEvent(
            productId,
            ProductRestModel.getTitle(),
            ProductRestModel.getPrice(),
            ProductRestModel.getQuantity());

    LOGGER.info("Before publishing ProductCreatedEvent");

    // Adding a unique ID to a message Header.
    ProducerRecord<String, ProductCreatedEvent> record =
        new ProducerRecord<>("products-created-events-topic", productId, productCreatedEvent);
    record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

    // Synchronous Methond
    SendResult<String, ProductCreatedEvent> result =
        // kafkaTemplate.send("products-created-events-topic", productId,
        // productCreatedEvent).get();
        kafkaTemplate.send(record).get();

    // Asynchronous way of doing it
    //
    // CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
    //   kafkaTemplate.send("products-created-events-topic", productId, productCreatedEvent);
    //
    // future.whenComplete((result, exception) -> {
    //
    //   if(exception != null) {
    //     LOGGER.error("********** Failed to send message: " + exception.getMessage());
    //   } else {
    //     LOGGER.info("********** Message sent successfully: " + result.getRecordMetadata());
    //   }
    // });

    // If you add this line the code becomes sysnchronous but if you remove, it becomes
    // asynchronous.
    // future.join();
    LOGGER.info("Partition: " + result.getRecordMetadata().partition());
    LOGGER.info("Topic: " + result.getRecordMetadata().topic());
    LOGGER.info("Offset: " + result.getRecordMetadata().offset());
    LOGGER.info("********** Returning product id");

    return productId;
  }
}
