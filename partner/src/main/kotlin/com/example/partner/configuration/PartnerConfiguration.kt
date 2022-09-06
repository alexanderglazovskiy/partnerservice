package com.example.partner.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PartnerConfiguration {

    @Bean
    fun rateClient(): WebClient {
        return WebClient.create()
    }

    @Bean
    fun limitClient(): WebClient {
        return WebClient.create()
    }
}