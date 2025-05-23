package com.notrlyanurag.ws.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.notrlyanurag.ws.products.CreateProductRestModel;

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

    //TODO: Presist Product details into database table before publishing an Event.

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
        productId,
        ProductRestModel.getTitle(),
        ProductRestModel.getPrice(),
        ProductRestModel.getQuantity()
    );

    LOGGER.info("Before publishing ProductCreatedEvent");


// Synchronous Methond
    SendResult<String, ProductCreatedEvent> result = kafkaTemplate
      .send("products-created-events-topic", productId, productCreatedEvent)
      .get();

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

    // If you add this line the code becomes sysnchronous but if you remove, it becomes asynchronous.
    // future.join();
    LOGGER.info("Partition: " + result.getRecordMetadata().partition());
    LOGGER.info("Topic: " + result.getRecordMetadata().topic());
    LOGGER.info("Offset: " + result.getRecordMetadata().offset());   
    LOGGER.info("********** Returning product id");

    return productId;
  } }
