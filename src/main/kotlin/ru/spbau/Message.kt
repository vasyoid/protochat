package ru.spbau

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

data class Message(
    val author: String,
    val date: LocalDateTime,
    val message: String
) {

    override fun toString(): String {
        return author + ": " + date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.MEDIUM)) + "\n" + message
    }
}