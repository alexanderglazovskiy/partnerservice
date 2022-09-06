package com.example.rate.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("rates")
data class Rate(
    @Id
    val id: String,
    val name: String
)