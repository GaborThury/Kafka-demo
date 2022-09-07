package com.epam.kafka.service;

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
    private static final String TOPIC_NAME = "notification";
    private static final Logger LOGGER = java.util.logging.Logger.getLogger(NotificationProducer.class.getName());


    public void send(String message) {
        Properties properties = initProperties();
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);

        producer.send(record, ((recordMetadata, e) -> {
            if (e == null) {
                LOGGER.info("Received new metadata, \n" +
                        "Topic: " + recordMetadata.topic() + "\n" +
                        "Partition " + recordMetadata.partition() + "\n" +
                        "Offset: " + recordMetadata.offset() + "\n" +
                        "Timestamp: " + recordMetadata.timestamp());
            } else {
                LOGGER.severe("Error while producing");
            }
        }));

        producer.close();
    }

    private Properties initProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_URL);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

}
