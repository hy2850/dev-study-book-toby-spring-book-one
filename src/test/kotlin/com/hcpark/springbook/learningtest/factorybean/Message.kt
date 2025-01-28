package com.hcpark.springbook.learningtest.factorybean

class Message private constructor(
    val text: String
) {
    companion object {
        fun newMessage(text: String): Message {
            return Message(text)
        }
    }
}