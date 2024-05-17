package hust.thehs

import hust.thehs.core.Initializer
import hust.thehs.plugins.*
import hust.thehs.plugins.configureHTTP
import hust.thehs.plugins.configureRouting
import hust.thehs.plugins.configureSecurity
import hust.thehs.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes", "resources"), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
