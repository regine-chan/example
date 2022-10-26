package com.example.mail_system

import com.example.model.DealModel
import com.example.model.MailStatus
import com.example.plugins.configureSerialization
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

class MailServer(port: Int, wait: Boolean = false) {

    init {
        embeddedServer(CIO, port = port, host = "0.0.0.0") {
            configureSerialization()
            routing {
                post("/send-deal") {
                    val dealModel = call.receive<DealModel>()
                    if (dealModel.customerModel.email.contains("@")) {
                        call.respond(MailStatus.DELIVERED)
                    }
                    call.respond(HttpStatusCode.BadRequest, MailStatus.BOUNCED)
                }
            }
        }.start(wait = wait)
    }
}
