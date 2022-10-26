package com.example.integration_layer

import com.example.model.BuyInsuranceRequest
import com.example.plugins.configureSerialization
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.default
import io.ktor.server.http.content.files
import io.ktor.server.http.content.static
import io.ktor.server.http.content.staticRootFolder
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import java.io.File

class IntegrationLayerServer(port: Int, wait: Boolean = false) {

    private val insuranceService = InsuranceService()

    init {
        embeddedServer(CIO, port = port, host = "0.0.0.0") {
            configureSerialization()

            install(StatusPages) {
                exception<ClientRequestException> { call, cause ->
                    call.respond(cause.response.status, cause.response.bodyAsText())
                }
            }

            routing {
                post("/insurance") {
                    val request = call.receive<BuyInsuranceRequest>()
                    call.respond(insuranceService.buyInsurance(request))
                }

                static("/") {
                    staticRootFolder = File("my-app/build")
                    default("index.html")
                    files(".")
                }
            }
        }.start(wait = wait)
    }
}
