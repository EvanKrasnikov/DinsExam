package ru.geographer29.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaHandler {

    private static final KafkaProducer<String, String> producer;
    private static final String topic = "alert";

    static {
        String kafkaServer = "kafka_server:9091";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);

        producer = new KafkaProducer<String, String>(properties);
    }

    public static void notifyKafka(String message){
        producer.send(new ProducerRecord<String, String>(topic, message));
    }

}
