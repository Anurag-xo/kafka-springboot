package com.notrlyanurag.ws.products;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.rocksdb.util.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import kafka.zk.ConsumerOffset;


@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test") // it will look for application-test.properties
@EmbeddedKafka(partitions = 3, count = 3, controlledShutdown = true)
@SpringBootTest(
    properties = "spring.kafka.producer.bootstrap-servers=${spring.emmbedded.kafka.brokers}")
public class ProductServiceintegraionTest {

  @Autowired
  private ProductService productService;
  
  @Autowired
  private EmbeddedKafkaBroker embeddedKafkaBroker;

  @Autowired
  Environment environment;

  @Test
  void testCreateProduct_whenGivenValidProductDetails_successfullSendsKafkaMessage() {

    // Arrange

    String title="iPhone 11";
    BigDecimal price = new BigDecimal(600);
    Integer quantity = 1;

    CreateProductRestModel createProductRestModel = new CreateProductRestModel();
    createProductRestModel.setPrice(price);
    createProductRestModel.setQuantity(quantity);
    createProductRestModel.setTitle(title);

    // Act
    productService.createProduct(CreateProductRestModel);



    // assert
  }

  private Map<String, Object> getConsumerProperties() {
    return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
        ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class,
        ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"),
        JsonDeserializer.TRUSTED_PACKAGES, environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages");
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, environment.getProperty("spring.kafka.consumer.auto-offset-reset")
        );
  }
}
