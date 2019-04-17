package ru.spbau

import javafx.application.Application

/**
 * Main class.
 * Launches the application.
 */
object Main {

    /**
     * Main function.
     * @param args command line arguments.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.size != 2 && args.size != 3) {
            System.err.println("incorrect number of arguments: must be 2 or 3")
            return
        }
        Application.launch(ChatView::class.java, *args)
    }
}