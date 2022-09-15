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
public class OrderProducer {

    private static final String BOOTSTRAP_SERVERS_URL = "127.0.0.1:9092";
    public static final String ORDER_TOPIC = "order";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(OrderProducer.class.getName());


    public void produce(Order order) {
        Properties properties = initProperties();
        KafkaProducer<String, Order> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, Order> record = new ProducerRecord<>(ORDER_TOPIC, order);

        LOGGER.info("sending order to the order topic " + order);
        producer.send(record);
        producer.close();
    }

    private static Properties initProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderSerializer.class.getName());
        return properties;
    }
}
