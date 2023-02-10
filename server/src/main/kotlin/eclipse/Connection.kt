package eclipse

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

typealias ConnectionId = Int
class Connection(val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger(0)
    }
    val id : ConnectionId = lastId.getAndIncrement()
}