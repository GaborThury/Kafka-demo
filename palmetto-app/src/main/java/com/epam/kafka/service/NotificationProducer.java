package com.epam.kafka.service;

import com.epam.kafka.domain.Order;
import com.epam.kafka.serializer.OrderSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.logging.Logger;

@Service
public class NotificationProducer {

    private static final String BOOTSTRAP_SERVERS_URL = "127.0.0.1:9092";
    private static final String NOTIFICATION_TOPIC = "notification";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(NotificationProducer.class.getName());


    public void send(Order order) {
        Properties properties = initProperties();
        KafkaProducer<String, Order> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, Order> record = new ProducerRecord<>(NOTIFICATION_TOPIC, order);

        LOGGER.info("sending order to the notification topic " + order);
        producer.send(record);
        producer.close();
    }

    private Properties initProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class.getName());
        return properties;
    }

}
