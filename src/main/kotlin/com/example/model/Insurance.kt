package com.example.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Insurance

@Serializable
data class CarInsurance(
    val registrationNumber: String,
    val bonusPercentage: Int
) : Insurance()
