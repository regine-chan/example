package com.example

import com.example.model.BuyInsuranceRequest
import com.example.model.CarInsurance
import com.example.model.CustomerDataModel
import com.github.javafaker.Faker
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlin.random.Random.Default.nextInt
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

open class IntegrationTestSetup {

    companion object {
        private val httpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json)
            }

            defaultRequest {
                port = 8083
                headers {
                    contentType(ContentType.Application.Json)
                }
            }
        }

        private val faker = Faker()

        fun buyInsurance(buyInsuranceRequest: BuyInsuranceRequest): HttpResponse = runBlocking {
            return@runBlocking httpClient.post("/insurance") {
                setBody(buyInsuranceRequest)
            }
        }

        fun createBuyCarInsuranceRequest(
            bonusPercentage: Int = nextInt(0, 100),
            nationalIdentityNumber: Long = faker.number().digits(11).toLong(),
            email: String = faker.internet().emailAddress()
        ) = BuyInsuranceRequest(
            CarInsurance(
                registrationNumber = faker.beer().malt(),
                bonusPercentage
            ),
            CustomerDataModel(
                nationalIdentityNumber,
                faker.name().firstName(),
                faker.name().lastName(),
                email
            )
        )

        init {
            main(arrayOf("test"))
        }
    }
}