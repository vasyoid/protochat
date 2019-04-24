package ru.spbau.backend

import com.rabbitmq.client.*
import javafx.collections.ObservableList
import ru.spbau.Message
import java.time.LocalDateTime
import java.nio.charset.Charset


class ChatBackend(
    private val userName: String,
    private val messageList: ObservableList<Message>,
    host: String,
    port: Int
) {
    private val channel: Channel
    private val connection: Connection


    init {
        val factory = ConnectionFactory()
        factory.host = host
        factory.port = port
        connection = factory.newConnection()
        channel = connection.createChannel()
        channel.queueDeclare(QUEUE_NAME, false, false, false, null)

        val consumer: Consumer = object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String, envelope: Envelope,
                properties: AMQP.BasicProperties, body: ByteArray
            ) {
                val message = String(body, Charset.defaultCharset())
                println(message)
                messageList.add(Message.deserialize(message))
            }
        }

        channel.basicConsume(QUEUE_NAME, true, consumer)
    }

    fun send(text: String) {
        val message = Message(userName, LocalDateTime.now(), text)
        val cnt = channel.consumerCount(QUEUE_NAME)
        for (i in 0 until cnt) {
            channel.basicPublish("", QUEUE_NAME, null, message.toString().toByteArray())
        }
    }

    companion object {
        private const val QUEUE_NAME = "queue_chat"
    }
}