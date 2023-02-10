package eclipse

import com.google.protobuf.Message
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.io.File
import java.io.FileInputStream
import java.util.*


class GameServerConfiguration(path: String)
{
    init {
        val file = File(path)
        val prop = Properties()
        FileInputStream(file).use { prop.load(it) }

        prop.stringPropertyNames().associateWith {prop.getProperty(it)}
    }
}

abstract class GameServer() {
    protected abstract val game: Game

    private val connections = Collections.synchronizedSet<Connection>(LinkedHashSet())
    suspend fun handleNewSession(session: DefaultWebSocketServerSession) {
        val connection = Connection(session)
        connections += connection
        try {
            for (frame in connection.session.incoming) {
                handleFrame(frame, connection)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        } finally {
            connections -= connection
        }
    }
    private fun handleFrame(frame: Frame, connection: Connection)
    {
        try {
            validateCredentials(frame, connection)?.
            handleServerRequest(frame, connection)?.
            handleGameRequest(frame, connection.id)
        } catch (e : Exception) {
            sendErrorMessage(e)
        }
    }

    private fun sendErrorMessage(e: Exception): Nothing = TODO("Not yet implemented")
    private fun handleGameRequest(frame: Frame, id: ConnectionId) = game.receiveMessage(frame, id)
    abstract fun handleServerRequest(frame: Frame, connection: Connection) : GameServer?
    abstract fun validateCredentials(frame: Frame, connection: Connection) : GameServer?
}
