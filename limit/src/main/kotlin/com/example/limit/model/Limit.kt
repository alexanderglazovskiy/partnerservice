package com.example.limit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("limits")
data class Limit(
    @Id
    val id: String,
    val name: String
)