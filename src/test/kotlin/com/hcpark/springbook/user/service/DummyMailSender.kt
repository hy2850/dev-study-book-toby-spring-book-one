package com.hcpark.springbook.user.service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

// Fake mail sending funciton
class DummyMailSender : MailSender {

    override fun send(vararg simpleMessages: SimpleMailMessage?) {}
}