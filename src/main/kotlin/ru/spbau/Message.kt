package ru.spbau

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Message(
    val author: String,
    val date: LocalDateTime,
    val message: String
) {

    override fun toString() = author + "\n" + date.format(FMT) + "\n" + message

    companion object {

        private val FMT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)

        fun deserialize(text: String): Message {
            val tokens = text.split("\n")
            val author = tokens[0]
            val date = LocalDateTime.parse(tokens[1], FMT)
            val message = tokens[2]
            return Message(author, date, message)
        }
    }
}