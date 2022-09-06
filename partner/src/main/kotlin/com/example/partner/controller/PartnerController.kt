package com.example.partner.controller

import com.example.partner.model.Partner
import com.example.partner.repository.PartnerRepository
import com.example.partner.service.LimitService
import com.example.partner.service.RateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/partners")
class PartnerController @Autowired constructor(
    val repository: PartnerRepository, val rateService: RateService, val limitService: LimitService
) {

    @GetMapping("/{id}")
    fun getPartner(@PathVariable id: String): Mono<ResponseEntity<String>> {
        return repository.findById(id)
            .flatMap { partner ->
                limitService.getLimit(partner.limitId)
                    .zipWith(rateService.getRate(partner.rateId))
                    .map {
                        ResponseEntity.ok()
                            .body("partner_id: ${partner.id} partner.name: ${partner.name}  limit.name: ${it.t1.name} rate.name: ${it.t2.name} limit.id: ${it.t1.id} rate.id: ${it.t2.id}")
                    }
            }
            .switchIfEmpty(ResponseEntity.status(HttpStatus.OK).body("Partner $id not found").toMono())
            .onErrorResume { ResponseEntity.status(HttpStatus.NO_CONTENT).body(it.message).toMono() }
    }

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createPartner(@RequestBody partnerMono: Mono<Partner>): Mono<ResponseEntity<String>> {
        return partnerMono
            .flatMap { partner ->
                limitService.getLimit(partner.limitId)
                    .zipWith(rateService.getRate(partner.rateId))
                    .flatMap {
                        repository.save(partner)
                            .map { ResponseEntity.ok().body(it.toString()) }
                    }
                    .onErrorResume { ResponseEntity.badRequest().body(it.message).toMono() }
            }
    }
}