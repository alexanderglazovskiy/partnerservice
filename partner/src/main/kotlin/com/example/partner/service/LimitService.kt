package com.example.partner.service

import com.example.partner.dto.LimitDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class LimitService @Autowired constructor(private val limitClient: WebClient) {

    @Value("\${limit-url:}")
    lateinit var limitUrl: String

    fun getLimit(id: String?): Mono<LimitDto> {
        return limitClient
            .get()
            .uri("$limitUrl/limits/$id")
            .retrieve()
            .onStatus({ httpStatus -> httpStatus == HttpStatus.NOT_FOUND },
                { Mono.error(java.lang.IllegalArgumentException("Limit $id not found")) })
            .bodyToMono(LimitDto::class.java)
    }
}