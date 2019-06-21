package peng.devs

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun main() {
  embeddedServer(Netty, 8080) {
    install(CallLogging) { level = Level.INFO }

    routing {
      var available = true

      get ("/hello") {
        call.respond("Hi, I'm Ktor server")
      }

      get ("/health") {
        call.respond(if (available) OK else ServiceUnavailable)
      }

      put ("/shutdown") {
        available = false
        call.respond(OK)
      }
    }
  }.start(wait = true)
}
