package com.example.limit.controller

import com.example.limit.dto.LimitDto
import com.example.limit.model.Limit
import com.example.limit.repository.LimitRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/limits")
class LimitController @Autowired constructor(private val repository: LimitRepository) {

    @GetMapping("/{id}")
    fun getLimit(@PathVariable id: String): Mono<ResponseEntity<LimitDto>> {
        return repository.findById(id).map { ResponseEntity.ok().body(LimitDto(it.id, it.name)) }
            .switchIfEmpty(ResponseEntity.notFound().build<LimitDto>().toMono())
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createLimit(@RequestBody limitMono: Mono<Limit>): Mono<ResponseEntity<Limit>> {
        return limitMono.flatMap { limit -> repository.save(limit).map { ResponseEntity.ok().body(it) } }
    }
}
