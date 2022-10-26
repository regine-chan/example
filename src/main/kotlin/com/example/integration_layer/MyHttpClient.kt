package com.example.integration_layer

import com.example.model.CustomerDataModel
import com.example.model.CustomerModel
import com.example.model.DealDataModel
import com.example.model.DealModel
import com.example.model.DealStatus
import com.example.model.MailStatus
import com.example.model.toModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.port
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class MyHttpClient {

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }

        expectSuccess = true

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    fun createCustomer(customerDataModel: CustomerDataModel): CustomerModel = runBlocking {
        val response = httpClient.post("/customers") {
            port = 8081
            setBody(customerDataModel)
        }.body<String>().toLong()

        return@runBlocking customerDataModel.toModel(response)
    }

    fun createDeal(dealDataModel: DealDataModel): DealModel = runBlocking {
        val response = httpClient.post("/deals") {
            port = 8081
            setBody(dealDataModel)
        }.body<Long>()

        return@runBlocking dealDataModel.toModel(response)
    }

    fun updateDeal(dealModel: DealModel): DealModel = runBlocking {
        val response = httpClient.put("/deals/${dealModel.id}") {
            port = 8081
            setBody(dealModel)
        }.body<DealStatus>()

        return@runBlocking dealModel.copy(dealStatus = response)
    }

    fun sendDealMail(dealModel: DealModel): MailStatus = runBlocking {
        httpClient.post("/send-deal") {
            port = 8082
            setBody(dealModel)
        }.body()
    }
}
