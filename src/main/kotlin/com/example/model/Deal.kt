package com.example.model

import kotlinx.serialization.Serializable
import kotlin.random.Random.Default.nextLong

@Serializable
data class DealModel(
    val id: Long,
    val insurance: Insurance,
    val customerModel: CustomerModel,
    val dealStatus: DealStatus = DealStatus.CREATED
)

@Serializable
data class DealDataModel(
    val insurance: Insurance,
    val customerModel: CustomerModel
)

fun DealDataModel.toModel(id: Long = nextLong()) = DealModel(
    id = id,
    insurance = insurance,
    customerModel = customerModel,
    dealStatus = DealStatus.CREATED
)

enum class DealStatus(val value: String) {
    SENT("deal sent"),
    CREATED("deal created"),
    FAILED("deal processing failed")
}
