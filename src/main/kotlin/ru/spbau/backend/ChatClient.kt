package ru.spbau.backend

import io.grpc.ManagedChannelBuilder
import ru.spbau.Message
import ru.spbau.ProtoChatGrpc
import ru.spbau.ProtoChatGrpc.ProtoChatBlockingStub
import ru.spbau.Protochat

class ChatClient(
    userName: String,
    port: Int,
    address: String = ""
) : ChatBackend(userName) {

    private val stub: ProtoChatBlockingStub

    init {
        val channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build()
        stub = ProtoChatGrpc.newBlockingStub(channel)
    }

    override fun sendMessage(message: Message): Message {
        try {
            stub.sendMessage(message.serialize())
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        return message
    }

    override fun receive(): List<Message> {
        try {
            return stub.receiveMessage(Protochat.Empty.getDefaultInstance())
                .asSequence()
                .toList()
                .map { Message.deserialize(it) }
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        return listOf()
    }
}