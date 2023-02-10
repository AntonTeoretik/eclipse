package eclipse

import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.ConcurrentHashMap

abstract class Game {
    private val channel = Channel<Request>()

    private val players = ConcurrentHashMap<PlayerId, Player>()


    suspend fun sendRequest(request: Request) {
        channel.send(request)
    }

    suspend fun mainCycle() {
        for (message in channel) {
            this.checkRequest(message)?.proceedRequest(message)
        }
    }

    abstract fun proceedRequest(request: Request)

    abstract fun checkRequest(request: Request) : Game?
    fun receiveMessage(frame: Frame, id: ConnectionId): Unit {
        TODO("Not yet implemented")
    }
}
