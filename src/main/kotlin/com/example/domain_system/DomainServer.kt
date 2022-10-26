package com.example.domain_system

import com.example.model.CustomerDataModel
import com.example.model.CustomerModel
import com.example.model.DealDataModel
import com.example.model.DealModel
import com.example.model.DealStatus
import com.example.model.toModel
import com.example.plugins.configureSerialization
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import io.ktor.server.util.getOrFail

class DomainServer(port: Int, wait: Boolean = false) {

    private val customers = mutableSetOf<CustomerModel>()
    private val deals = mutableSetOf<DealModel>()

    init {
        embeddedServer(CIO, port = port, host = "0.0.0.0") {
            configureSerialization()
            routing {
                post("/customers") {
                    val customerDataModel = call.receive<CustomerDataModel>()

                    customers.firstOrNull { it.nationalIdentityNumber == customerDataModel.nationalIdentityNumber }
                        ?.let {
                            call.respond(mapOf("customerId" to it.id))
                        }

                    if (customerDataModel.nationalIdentityNumber.toString().length != 11) {
                        call.respond(HttpStatusCode.BadRequest, "National identity number is incorrect")
                    }

                    val newCustomer = customerDataModel.toModel()
                    customers.add(newCustomer)

                    call.respond(newCustomer.id)
                }

                post("/deals") {
                    val dealDataModel = call.receive<DealDataModel>()

                    val newDeal = dealDataModel.toModel()
                    deals.add(newDeal)

                    call.respond(newDeal.id)
                }

                put("/deals/{id}") {
                    val id = call.parameters.getOrFail("id").toLong()
                    val deal = call.receive<DealModel>()
                    deals.firstOrNull {
                        it.id == id
                    }?.let {
                        deals.remove(it)
                        deals.add(deal)
                        call.respond(deal.dealStatus)
                    }
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }.start(wait = wait)
    }
}
