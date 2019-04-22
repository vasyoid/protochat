package ru.spbau

import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import javafx.util.Duration
import ru.spbau.backend.ChatBackend
import ru.spbau.backend.ChatClient
import ru.spbau.backend.ChatServer


class ChatView : Application() {

    private lateinit var backend: ChatBackend
    private val messageList = FXCollections.observableArrayList<Message>()

    override fun start(primaryStage: Stage) {
        val args = parameters.unnamed
        primaryStage.title = "Проточат"
        if (args.size == 2) {
            backend = ChatServer(args[1], args[0].toInt())
            primaryStage.title += " (server mode)"
        } else {
            backend = ChatClient(args[2], args[1].toInt(), args[0])
            primaryStage.title += " (client mode)"
        }
        primaryStage.isResizable = false
        val pane = GridPane()
        val messageField = TextArea()
        messageField.promptText = "Bведите сообщение"
        messageField.prefHeight = COMPOSE_HEIGHT
        messageField.prefWidth = WINDOW_WIDTH - SEND_WIDTH
        val sendButton = Button("Oтправить")
        sendButton.prefHeight = COMPOSE_HEIGHT
        sendButton.prefWidth = SEND_WIDTH
        val messageListView = ListView(messageList)
        messageListView.prefHeight = WINDOW_HEIGHT - COMPOSE_HEIGHT
        sendButton.onMouseClicked = EventHandler {
            if (messageField.text.isNotEmpty()) {
                messageList.add(backend.send(messageField.text))
                messageField.text = ""
            }
        }
        val fiveSecondsWonder = Timeline(
            KeyFrame(Duration.seconds(0.5), EventHandler<ActionEvent> {
                messageList.addAll(backend.receive())
            })
        )
        fiveSecondsWonder.cycleCount = Timeline.INDEFINITE
        fiveSecondsWonder.play()
        pane.add(messageField, 0, 0)
        pane.add(sendButton, 1, 0)
        pane.add(messageListView, 0, 1, 2, 1)
        val scene = Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT)
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        const val COMPOSE_HEIGHT = 60.0
        const val SEND_WIDTH = 100.0
        const val WINDOW_HEIGHT = 700.0
        const val WINDOW_WIDTH = 800.0
    }
}