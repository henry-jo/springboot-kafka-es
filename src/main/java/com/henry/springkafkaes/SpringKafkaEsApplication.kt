package com.henry.springkafkaes

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringKafkaEsApplication

fun main(args: Array<String>) {

    SpringApplication.run(SpringKafkaEsApplication::class.java, *args)
}