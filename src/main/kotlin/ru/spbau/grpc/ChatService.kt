package ru.spbau.grpc

import io.grpc.stub.StreamObserver
import ru.spbau.Message
import ru.spbau.ProtoChatGrpc
import ru.spbau.Protochat
import ru.spbau.backend.ChatServer
import java.time.LocalDateTime

class ChatService(val backend: ChatServer): ProtoChatGrpc.ProtoChatImplBase() {
    override fun sendMessage(request: Protochat.Message, responseObserver: StreamObserver<Protochat.Empty>) {
        val message = Message(request.name, LocalDateTime.now(), request.message)
        backend.receiveMessage(message)
        responseObserver.onNext(Protochat.Empty.getDefaultInstance())
        responseObserver.onCompleted()
    }

    override fun receiveMessage(request: Protochat.Empty, responseObserver: StreamObserver<Protochat.Message>) {
        backend.getOutgoing().forEach {
            responseObserver.onNext(it.serialize())
        }
        responseObserver.onCompleted()
    }
}