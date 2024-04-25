package lms.nar.companion.api

import io.ktor.server.application.*

fun Application.onServerReady(block: () -> Unit) {
    environment.monitor.subscribe(ServerReady) {
        block()
    }
}