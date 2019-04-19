package ru.spbau

import io.grpc.ManagedChannelBuilder
import ru.spbau.ProtoChatGrpc.ProtoChatBlockingStub

class ChatBackend(
    private val port: Int,
    private val userName: String,
    private val address: String = ""
) {

    private val isServer: Boolean = address.isNotEmpty()
    private val stub: ProtoChatBlockingStub

    init {
        val channel = ManagedChannelBuilder.forAddress(address, port)
            .usePlaintext()
            .build()
        stub = ProtoChatGrpc.newBlockingStub(channel)

    }

    fun send(message: Message) {

    }

    fun receive() {

    }
}