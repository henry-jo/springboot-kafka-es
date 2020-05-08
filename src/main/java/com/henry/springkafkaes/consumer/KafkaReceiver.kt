package com.henry.springkafkaes.consumer

import mu.KLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.Properties
import javax.annotation.PostConstruct

@Component
class KafkaReceiver {

    companion object : KLogging()

    private var consumer: KafkaConsumer<String, String>? = null

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootStrapServer: String

    @Value("\${spring.kafka.consumer.key-deserializer}")
    private lateinit var keyDeserializer: String

    @Value("\${spring.kafka.consumer.value-deserializer}")
    private lateinit var valueDeserializer: String

    @Value("\${spring.kafka.template.default-topic}")
    private lateinit var topicName: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private lateinit var offsetReset: String

    @Value("\${spring.kafka.consumer.max-poll-records}")
    private lateinit var maxPollRecords: String

    @Value("\${spring.kafka.consumer.enable-auto-commit}")
    private lateinit var enableAutoCommit: String

    @PostConstruct
    fun build() {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer)
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer)
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer)
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset)
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords)
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit)
        consumer = KafkaConsumer(properties)
    }

    @KafkaListener(topics = ["\${spring.kafka.template.default-topic}"])
    fun consume(@Headers headers: MessageHeaders, @Payload payload: String) {
        logger.info("CONSUME HEADERS : $headers")
        logger.info("CONSUME PAYLOAD : $payload")
    }
}