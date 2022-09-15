package com.epam.kafka.service;

import com.epam.kafka.deserializer.OrderDeserializer;
import com.epam.kafka.domain.Order;
import com.epam.kafka.domain.OrderStatus;
import com.epam.kafka.serializer.OrderSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final String BOOTSTRAP_SERVERS_URL = "127.0.0.1:9092";
    public static final String NOTIFICATION_TOPIC = "notification";
    public static final String GROUP_ID = "order_group";
    public static final String EARLIEST = "earliest";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(NotificationService.class.getName());


    @EventListener(ApplicationReadyEvent.class)
    public void consume() {
        Properties properties = initConsumerProperties();
        KafkaConsumer<String, Order> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton(NOTIFICATION_TOPIC));

        while (true) {
            ConsumerRecords<String, Order> records = consumer.poll(Duration.ofMillis(10000));

            records.forEach(record -> {
                Order order = record.value();
                if (OrderStatus.READY_FOR_DELIVERY.equals(order.getOrderStatus())) {
                    LOGGER.info("Changing status to in delivery for order: " + order);
                    order.setOrderStatus(OrderStatus.IN_DELIVERY);
                    produce(order);
                }
            });
        }
    }

    public void produce(Order order) {
        Properties properties = initProducerProperties();
        KafkaProducer<String, Order> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, Order> record = new ProducerRecord<>(NOTIFICATION_TOPIC, order);

        LOGGER.info("sending order to the notification topic " + order);
        producer.send(record);
        producer.close();
    }

    private Properties initConsumerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);

        return properties;
    }

    private Properties initProducerProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class.getName());
        return properties;
    }
}
