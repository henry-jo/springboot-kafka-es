package com.henry.springkafkaes.controller

import com.henry.springkafkaes.producer.KafkaSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kafka")
class KafkaApiController {

    @Autowired
    private lateinit var producer: KafkaSender

    @PostMapping("/send-message")
    fun sendMessage(@RequestBody message: String): ResponseEntity<Unit> {
        producer.send(message)
        return ResponseEntity.noContent().build()
    }
}