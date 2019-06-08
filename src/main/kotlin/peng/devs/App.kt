package peng.devs

import io.ktor.application.call
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

var AVAILABLE = true

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            get ("/") {
                call.respond(if (AVAILABLE) "Hello, there" else ServiceUnavailable)
            }

            get ("/health") {
                call.respond(if (AVAILABLE) OK else ServiceUnavailable)
            }

            put ("/shutdown") {
                AVAILABLE = false
                call.respond(OK)
            }
        }
    }.start(wait = true)
}
