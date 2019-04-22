package ru.spbau.backend

import io.grpc.ServerBuilder
import ru.spbau.Message
import ru.spbau.grpc.ChatService

class ChatServer(
    userName: String,
    port: Int
) : ChatBackend(userName) {

    private val chatService: ChatService = ChatService(this)

    private val incoming = mutableListOf<Message>()
    private val outgoing = mutableListOf<Message>()

    init {
        ServerBuilder.forPort(port).addService(chatService).build().start()
    }

    fun getOutgoing(): List<Message> {
        synchronized(outgoing) {
            return outgoing.toList().also {
                outgoing.clear()
            }
        }
    }

    override fun sendMessage(message: Message): Message {
        synchronized(outgoing) {
            outgoing.add(message)
            return message
        }
    }

    override fun receive(): List<Message> {
        synchronized(incoming) {
            return incoming.toList().also {
                incoming.clear()
            }
        }
    }

    fun receiveMessage(message: Message) {
        synchronized(incoming) {
            incoming.add(message)
        }
    }
}