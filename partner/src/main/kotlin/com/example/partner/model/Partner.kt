package com.example.partner.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("partners")
data class Partner(
    @Id
    val id: String,
    val name: String,
    @Field("rate_id")
    @JsonProperty("rate_id")
    val rateId: String?,
    @Field("limit_id")
    @JsonProperty("limit_id")
    val limitId: String?,
)