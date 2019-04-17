package ru.spbau

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import java.time.LocalDateTime

class ChatView : Application() {

    private var isServer = true
    private var address: String = ""
    private var port = 0
    private var name = ""

    override fun start(primaryStage: Stage) {
        val args = parameters.unnamed
        if (args.size == 2) {
            isServer = false
            port = args[0].toInt()
            name = args[1]
        } else {
            address = args[0]
            port = args[1].toInt()
            name = args[2]
        }
        primaryStage.title = "Проточат"
        primaryStage.isResizable = false
        val pane = GridPane()
        pane.hgap = 4.0
        pane.vgap = 4.0
        val messageField = TextArea()
        messageField.promptText = "Bведите сообщение"
        messageField.minHeight = COMPOSE_HEIGHT
        messageField.maxHeight = COMPOSE_HEIGHT
        val sendButton = Button("Oтправить")
        sendButton.minHeight = COMPOSE_HEIGHT
        sendButton.maxHeight = COMPOSE_HEIGHT
        sendButton.minWidth = SEND_WIDTH
        val messageList = FXCollections.observableArrayList(Message("Петя", LocalDateTime.now(), "Привет"))
        val messageListView = ListView(messageList)
        sendButton.onMouseClicked = EventHandler {
                messageList.add(Message(name, LocalDateTime.now(), messageField.text))
        }
        pane.add(messageField, 0, 0)
        pane.add(sendButton, 1, 0)
        pane.add(messageListView, 0, 1, 2, 1)
        val scene = Scene(pane, 600.0, 600.0)
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        const val COMPOSE_HEIGHT = 60.0
        const val SEND_WIDTH = 100.0
    }
}