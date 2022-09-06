package com.example.rate.repository

import com.example.rate.model.Rate
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface RateRepository : ReactiveMongoRepository<Rate, String> {
}