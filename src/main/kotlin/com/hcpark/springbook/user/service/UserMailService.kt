package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.domain.User
import jakarta.mail.Authenticator
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.*

class UserMailService {

    companion object {
        private const val PORT = "465"
        private const val SMTPHOST = "smtp.gmail.com"

        private const val FROM: String = "grade-upgrade-notifier@gmail.com"
        private const val FROMNAME: String = "SpringBook"
    }

    fun sendUpgradeEMail(user: User) {
        val props = Properties();
        props.put("mail.transport.protocol", "smtp")
        props.put("mail.smtp.host", SMTPHOST)
        props.put("mail.smtp.port", PORT)
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.ssl.enable", "true")
        props.put("mail.smtp.ssl.trust", SMTPHOST)
        props.put("mail.smtp.starttls.required", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.ssl.protocols", "TLSv1.2")

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("USERNAME", "PWD")
            }
        })

        val message = MimeMessage(session)

        try {
            message.setFrom(InternetAddress(FROM, FROMNAME))
            message.addRecipient(MimeMessage.RecipientType.TO, InternetAddress(user.email))
            message.setSubject("Upgrade 안내")
            message.setText("사용자님의 등급이 ${user.level.name}로 업그레이드 되었습니다.")

            Transport.send(message)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}