package ru.spbau

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Message(
    val author: String,
    val date: LocalDateTime,
    val message: String
) {

    fun serialize(): Protochat.Message = Protochat.Message.newBuilder()
        .setName(author)
        .setMessage(message)
        .build()

    override fun toString() = author + ", " + date.format(FMT) + ":\n" + message

    companion object {

        private val FMT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)

        fun deserialize(message: Protochat.Message) = Message(message.name, LocalDateTime.now(), message.message)
    }
}