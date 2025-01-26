package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User
import org.springframework.mail.MailSender

// Store upgrade eligible emails into the list for testing
class UserMailServiceMock(
    mailSender: MailSender
) : UserMailService(mailSender) {

    val userEmails: MutableList<String> = mutableListOf()

    override fun sendUpgradeEMail(user: User) {
        userEmails.add(user.email)
        super.sendUpgradeEMail(user)
    }
}