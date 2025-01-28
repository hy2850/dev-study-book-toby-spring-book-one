package com.hcpark.springbook.learningtest.factorybean

import org.springframework.context.annotation.Bean

class FactoryBeanTestConfig {

//    @Bean
//    fun getMessage(): Message {
//        return Message.newMessage("Factory Bean")
//    }

    @Bean(name = ["message"])
    fun createMessageBean(): MessageFactoryBean {
        return MessageFactoryBean("Factory Bean")
    }
}