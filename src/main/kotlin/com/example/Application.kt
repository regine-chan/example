package com.example

import com.example.domain_system.DomainServer
import com.example.integration_layer.IntegrationLayerServer
import com.example.mail_system.MailServer

fun main(args: Array<String>) {
    DomainServer(8081)
    MailServer(8082)
    IntegrationLayerServer(8083, !args.contains("test"))
}
