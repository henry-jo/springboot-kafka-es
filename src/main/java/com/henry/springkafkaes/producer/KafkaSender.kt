package com.henry.springkafkaes.producer

import mu.KLogging
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Properties
import javax.annotation.PostConstruct

@Component
class KafkaSender {

    companion object : KLogging()

    private var producer: KafkaProducer<String, String>? = null

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootStrapServer: String

    @Value("\${spring.kafka.producer.key-serializer}")
    private lateinit var keySerializer: String

    @Value("\${spring.kafka.producer.value-serializer}")
    private lateinit var valueSerializer: String

    @Value("\${spring.kafka.template.default-topic}")
    private lateinit var topicName: String

    @PostConstruct
    fun build() {
        val properties = Properties()
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer)
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer)
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer)

        producer = KafkaProducer(properties)
    }

    fun send(message: String) {
        val prd = ProducerRecord<String, String>(topicName, message)

        try {
            producer?.send(prd) { _, e ->
                if (e != null) {
                    logger.error(e.message)
                }
            }
        } catch (e: Exception) {
            logger.error(e.message)
            e.printStackTrace()
        } finally {
            logger.info("SEND SUCCESS : $message")
            producer?.close()
        }
    }
}