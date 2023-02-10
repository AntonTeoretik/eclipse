package eclipse

import com.google.protobuf.Message


typealias PlayerId = String;
class Player (private val login : String,
              private val gameServer: GameServer) {
    public fun getLogin() = this.login
    public fun sayHello() = "Hi, I'm " + this.login + "\n"

    private var connection : Connection? = null

    public fun sendResponse(msg : Message) {

    }

    public fun attachConnection(connection: Connection) {
        this.connection = connection
    }
}
