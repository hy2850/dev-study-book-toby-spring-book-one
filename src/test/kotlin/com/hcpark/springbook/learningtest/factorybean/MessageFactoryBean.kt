package com.hcpark.springbook.learningtest.factorybean

import org.springframework.beans.factory.FactoryBean

class MessageFactoryBean(
    private val text: String
) : FactoryBean<Message> {

    override fun getObject(): Message {
        return Message.newMessage(text)
    }

    override fun getObjectType(): Class<*> {
        return Message::class.java
    }
}