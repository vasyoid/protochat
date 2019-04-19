package ru.spbau

import io.grpc.stub.StreamObserver
import java.time.LocalDateTime

class ChatService: ProtoChatGrpc.ProtoChatImplBase() {
    override fun sendMessage(request: Protochat.Message, responseObserver: StreamObserver<Protochat.Empty>) {
        val message = Message(request.name, LocalDateTime.now(), request.message)
        responseObserver.onCompleted()
    }

    override fun receiveMessage(request: Protochat.Empty, responseObserver: StreamObserver<Protochat.Message>) {
    }
}