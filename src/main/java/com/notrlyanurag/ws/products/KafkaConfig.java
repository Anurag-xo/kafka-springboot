package com.notrlyanurag.ws.products;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.producer.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.producer.key-serializer}")
  private String KeySerializer;

  @Value("${spring.kafka.producer.value-serializer}")
  private String valueSerializer;

  @Value("${spring.kafka.producer.producer.acks}")
  private String acks;

  @Value("${spring.kafka.producer.producer.properties.delivery.timeout.ms}")
  private String deliveryTimeout;

  @Value("${spring.kafka.producer.producer.linger.ms}")
  private String linger;

  @Value("${spring.kafka.producer.producer.request.timeout.ms}")
  private String requestTimeout;

  @Value("${spring.kafka.producer.properties.enable.idempotence}")
  private boolean idempotence;

  @Value("${spring.kafka.producer.properties.max.in.flight.requests.per.connection}5")
  private Integer inflightRequests;

  Map<String, Object> producerConfigs() {
    Map<String, Object> config = new HashMap<>();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KeySerializer);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    config.put(ProducerConfig.ACKS_CONFIG, acks);
    config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
    config.put(ProducerConfig.LINGER_MS_CONFIG, linger);
    config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
    config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, inflightRequests);
    // config.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);

    // config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config);

    return config;
  }

  @Bean
  ProducerFactory<String, ProductCreatedEvent> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  KafkaTemplate<String, ProductCreatedEvent> KafkaTemplate() {
    return new KafkaTemplate<String, ProductCreatedEvent>(producerFactory());
  }

  @Bean
  NewTopic createTopic() {
    return TopicBuilder.name("products-created-events-topic")
        .partitions(3)
        .replicas(3)
        .configs(Map.of("min.insync.replicas", "2"))
        .build();
  }
}
