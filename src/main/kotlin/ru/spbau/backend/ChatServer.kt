package ru.spbau.backend

import io.grpc.ServerBuilder
import ru.spbau.Message
import ru.spbau.grpc.ChatService

class ChatServer(
    userName: String,
    port: Int
) : ChatBackend(userName) {

    private val chatService: ChatService = ChatService(this)

    private var incoming = mutableListOf<Message>()
    private var outgoing = mutableListOf<Message>()

    init {
        ServerBuilder.forPort(port).addService(chatService).build().start()
    }

    fun getOutgoing(): List<Message> {
        val old = mutableListOf<Message>()
        old.addAll(outgoing)
        outgoing = mutableListOf()
        return old
    }

    override fun sendMessage(message: Message): Message {
        outgoing.add(message)
        return message
    }

    override fun receive(): List<Message> {
        val old = mutableListOf<Message>()
        old.addAll(incoming)
        incoming = mutableListOf()
        return old
    }

    fun receiveMessage(message: Message) {
        incoming.add(message)
    }
}