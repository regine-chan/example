package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class BuyInsuranceRequest(
    val insurance: Insurance,
    val customer: CustomerDataModel
)
