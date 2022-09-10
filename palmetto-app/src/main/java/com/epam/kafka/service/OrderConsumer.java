package com.epam.kafka.service;

import com.epam.kafka.deserializer.OrderDeserializer;
import com.epam.kafka.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class OrderConsumer {

    private final BakerService bakerService;
    private static final String BOOTSTRAP_SERVERS_URL = "127.0.0.1:9092";
    public static final String ORDER_TOPIC = "order";
    public static final String GROUP_ID = "order_group";

    public static final String EARLIEST = "earliest";

    public OrderConsumer(BakerService bakerService) {
        this.bakerService = bakerService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consume() {
        Properties properties = initProperties();
        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton(ORDER_TOPIC));

        // poll the new data
        while (true) {
            ConsumerRecords<String, Order> records = consumer.poll(Duration.ofMillis(1000));

            records.forEach(record -> {
                Order order = record.value();

                bakerService.bake(order);

                //LOGGER.info("Key: " + record.key());
                //LOGGER.info("Value: " + record.value());
                //LOGGER.info("Partition: " + record.partition());
                //LOGGER.info("Offset: " + record.offset());
            });

        }
    }
    private Properties initProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);

        return properties;
    }
}
