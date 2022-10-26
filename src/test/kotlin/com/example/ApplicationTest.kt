package com.example

import com.example.model.DealStatus
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlin.test.*
import kotlinx.coroutines.runBlocking

class ApplicationTest : IntegrationTestSetup() {

    @Test
    fun success(): Unit = runBlocking {
        val httpResponse = buyInsurance(createBuyCarInsuranceRequest())
        assertEquals(HttpStatusCode.OK, httpResponse.status)
        assertEquals(DealStatus.SENT, httpResponse.body<Pair<Long, DealStatus>>().second)
    }

    @Test
    fun invalidEmail(): Unit = runBlocking {
        val httpResponse = buyInsurance(createBuyCarInsuranceRequest(email = "invalid email"))
        assertEquals(HttpStatusCode.BadRequest, httpResponse.status)
    }

    @Test
    fun invalidNationalIdentityNumber(): Unit = runBlocking {
        val httpResponse = buyInsurance(createBuyCarInsuranceRequest(nationalIdentityNumber = 111))
        assertEquals(HttpStatusCode.BadRequest, httpResponse.status)
    }
}
