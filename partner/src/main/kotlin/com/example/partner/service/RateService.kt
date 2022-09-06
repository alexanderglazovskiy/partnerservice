package com.example.partner.service

import com.example.partner.dto.RateDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RateService @Autowired constructor(private val rateClient: WebClient) {

    @Value("\${rate-url:}")
    lateinit var rateUrl: String

    fun getRate(id: String?): Mono<RateDto> {
        return rateClient
            .get()
            .uri("$rateUrl/rates/$id")
            .retrieve()
            .onStatus({ httpStatus -> httpStatus == HttpStatus.NOT_FOUND },
                { Mono.error(IllegalArgumentException("Rate $id not found")) })
            .bodyToMono(RateDto::class.java)
    }
}