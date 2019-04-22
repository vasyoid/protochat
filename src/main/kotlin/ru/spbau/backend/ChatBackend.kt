package ru.spbau.backend

import ru.spbau.Message
import java.time.LocalDateTime

abstract class ChatBackend(
    private val userName: String
) {
    fun send(text: String): Message {
        val message = Message(userName, LocalDateTime.now(), text)
        return sendMessage(message)
    }

    abstract fun sendMessage(message: Message): Message

    abstract fun receive(): List<Message>

}