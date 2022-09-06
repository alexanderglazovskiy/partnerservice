package com.example.limit.repository

import com.example.limit.model.Limit
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface LimitRepository: ReactiveMongoRepository<Limit, String> {
}