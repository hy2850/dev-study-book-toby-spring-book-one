package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage

class UserMailService(
    private val mailSender: MailSender
) {

    companion object {
        private const val FROM: String = "grade-upgrade-notifier@gmail.com"
    }

    fun sendUpgradeEMail(user: User) {
        val mailMessage = SimpleMailMessage()

        mailMessage.setFrom(FROM)
        mailMessage.setTo(user.email)
        mailMessage.setSubject("Upgrade 안내")
        mailMessage.setText("사용자님의 등급이 ${user.level.name}로 업그레이드 되었습니다.")

        mailSender.send(mailMessage)
    }
}