package com.hcpark.springbook.user.service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class DummyMailSender : MailSender {

    override fun send(vararg simpleMessages: SimpleMailMessage?) {}
}