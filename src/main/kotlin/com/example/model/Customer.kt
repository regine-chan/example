package com.example.model

import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextLong

@Serializable
data class CustomerDataModel(
    val nationalIdentityNumber: Long,
    val firstName: String,
    val lastName: String,
    val email: String
)

@Serializable
data class CustomerModel(
    val id: Long,
    val nationalIdentityNumber: Long,
    val firstName: String,
    val lastName: String,
    val email: String
)

fun CustomerDataModel.toModel(id: Long = nextLong()) = CustomerModel(
    id = id,
    nationalIdentityNumber = nationalIdentityNumber,
    firstName = firstName,
    lastName = lastName,
    email = email
)
