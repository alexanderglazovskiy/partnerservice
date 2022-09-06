package com.example.partner.repository

import com.example.partner.model.Partner
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PartnerRepository : ReactiveMongoRepository<Partner, String> {
}