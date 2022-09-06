package com.example.rate.controller

import com.example.rate.dto.RateDto
import com.example.rate.model.Rate
import com.example.rate.repository.RateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/rates")
class RateController @Autowired constructor(private val repository: RateRepository) {

    @GetMapping("/{id}")
    fun getRate(@PathVariable id: String): Mono<ResponseEntity<RateDto>> {
        return repository.findById(id)
            .map { ResponseEntity.ok().body(RateDto(it.id, it.name)) }
            .switchIfEmpty(ResponseEntity.notFound().build<RateDto>().toMono())
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createRate(@RequestBody rateMono: Mono<Rate>): Mono<ResponseEntity<Rate>> {
        return rateMono.flatMap { rate -> repository.save(rate).map { ResponseEntity.ok().body(it) } }
    }
}