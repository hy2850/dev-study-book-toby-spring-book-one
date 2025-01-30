package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.dao.UserDaoMock
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.service.TestExceptionUserServiceMock.TestUserServiceException
import com.hcpark.springbook.user.transaction.TransactionHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cglib.proxy.Proxy
import org.springframework.context.ApplicationContext
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.PlatformTransactionManager
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var transactionManager: PlatformTransactionManager

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var dao: UserDao

    @MockitoBean
    private val mailSender: MailSender = DummyMailSender()

    companion object {
        private val userPark = User("park", "박씨", "123", Level.BASIC, 49, 0, "park@gmail.com")
        private val userKim = User("kim", "김씨", "567", Level.BASIC, 50, 0, "kim@gmail.com")
        private val userLee = User("lee", "이씨", "8910", Level.SILVER, 65, 29, "lee@gmail.com")
        private val userGo = User("go", "고씨", "111213", Level.SILVER, 65, 30, "go@gmail.com")
        private val userKwon = User("kwon", "권씨", "141516", Level.GOLD, 65, 30, "kwon@gmail.com")

        private val users: MutableList<User> = mutableListOf(
            userPark,
            userKim,
            userLee,
            userGo,
            userKwon
        )
    }

    @BeforeEach
    fun beforeEach() {
        users.forEach(dao::add)
    }

    @AfterEach
    fun afterEach() {
        dao.deleteAll()
    }

    @Test
    fun isBeanCreated() {
        assertNotNull(userService)
    }

    @Test
    fun upgradeLevel_noUpgrade() {
        val noUpgradeUser = userPark

        userService.upgradeLevel(noUpgradeUser)

        isLevelUpgradedFrom(noUpgradeUser, false)
    }

    @Test
    fun upgradeLevel_doUpgrade() {
        val upgradeUser = userKim

        userService.upgradeLevel(upgradeUser)

        isLevelUpgradedFrom(upgradeUser, true)
    }

    @Test
    fun upgradeLevel_exception() {
//        val mock = TestExceptionUserServiceMock(
//            transactionManager,
//            dao,
//            UserLevelUpgradePolicyDefault(),
//            UserMailService(DummyMailSender())
//        )
//        mock.setExceptionUserId(userKim.id)

        val userDaoMock = mock(UserDao::class.java)
        `when`(userDaoMock.update(userKim.upgradeLevel())).thenThrow(TestUserServiceException::class.java)

        val service = UserServiceImpl(
            userDaoMock,
            UserLevelUpgradePolicyDefault(),
            UserMailService(DummyMailSender())
        )

        assertDoesNotThrow { service.upgradeLevel(users[0]) }
        assertThrows(TestUserServiceException::class.java) { service.upgradeLevel(users[1]) }
    }

    @Test
    fun upgradeAllLevels() {
        val userDaoMock = mock(UserDao::class.java)
        `when`(userDaoMock.getAll()).thenReturn(users)

        val mailSenderMock = mock(MailSender::class.java)

        val service = UserServiceImpl(
            userDaoMock,
            UserLevelUpgradePolicyDefault(),
            UserMailService(mailSenderMock)
        )

        service.upgradeAllLevels()

        verify(userDaoMock, times(2)).update(any())
        verify(userDaoMock).update(userKim.upgradeLevel())
        verify(userDaoMock).update(userGo.upgradeLevel())

//        isLevelUpgradedFrom(userPark, false)
//        isLevelUpgradedFrom(userKim, true)
//        isLevelUpgradedFrom(userLee, false)
//        isLevelUpgradedFrom(userGo, true)
//        isLevelUpgradedFrom(userKwon, false)

        val mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage::class.java)
        verify(mailSenderMock, times(2)).send(mailMessageArg.capture())

        val mailMessages = mailMessageArg.allValues
            .map { it.to?.get(0) }
            .filterNotNull()
            .toCollection(mutableSetOf())

        assertThat(mailMessages.size).isEqualTo(2)
        assertTrue(mailMessages.contains(userKim.email))
        assertTrue(mailMessages.contains(userKim.email))
    }

    @Test
    @DisplayName("Test transaction rollback - need real dao instance")
    fun upgradeAllLevels_exception_transaction_rollback() {
        val mock = TestExceptionUserServiceMock(
            dao,
            UserLevelUpgradePolicyDefault(),
            UserMailService(DummyMailSender())
        )
        val userService = UserServiceTx(transactionManager, mock)

        mock.setExceptionUserId(userGo.id)

        assertThrows(TestUserServiceException::class.java) { userService.upgradeAllLevels() }

        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, false) // transaction rollback
        isLevelUpgradedFrom(userLee, false)
    }

    @Test
    @DisplayName("txUserService Dynamic proxy rollback test")
    fun upgradeAllLevels_exception_transaction_rollback_dynamic_proxy() {
        val mock = TestExceptionUserServiceMock(
            dao,
            UserLevelUpgradePolicyDefault(),
            UserMailService(DummyMailSender())
        )

        val txUserService = Proxy.newProxyInstance(
            javaClass.classLoader,
            arrayOf(UserService::class.java),
            TransactionHandler(mock, transactionManager, "upgradeAllLevels")
        ) as UserService

        mock.setExceptionUserId(userGo.id)

        assertThrows(TestUserServiceException::class.java) { txUserService.upgradeAllLevels() }

        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, false) // transaction rollback
        isLevelUpgradedFrom(userLee, false)
    }

    @Test
    @DirtiesContext
    @DisplayName("txUserService Dynamic proxy factory bean rollback test")
    fun upgradeAllLevels_exception_transaction_rollback_dynamic_proxy_factory_bean() {
        val mock = TestExceptionUserServiceMock(
            dao,
            UserLevelUpgradePolicyDefault(),
            UserMailService(DummyMailSender())
        )
        mock.setExceptionUserId(userGo.id)

        val txFactoryBean = applicationContext.getBean("&userService", ProxyFactoryBean::class.java)
        txFactoryBean.setTarget(mock)

        val txUserService = txFactoryBean.`object` as UserService

        assertThrows(TestUserServiceException::class.java) { txUserService.upgradeAllLevels() }

        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, false) // transaction rollback
        isLevelUpgradedFrom(userLee, false)
    }

    @Test
    fun upgradeAllLevels_email_mock_test() {
        val userDaoMock = UserDaoMock()
        userDaoMock.setUsers(userPark, userKim, userLee, userGo, userKwon)

        val userMailServiceMock = UserMailServiceMock(DummyMailSender())

        val mock = TestExceptionUserServiceMock(
            userDaoMock,
            UserLevelUpgradePolicyDefault(),
            userMailServiceMock
        )

        val userService = UserServiceTx(transactionManager, mock)
        userService.upgradeAllLevels()

        assertEquals(2, userDaoMock.updatedUsers.size)

        assertEquals(2, userMailServiceMock.userEmails.size)
        assertTrue(userMailServiceMock.userEmails.contains(userKim.email))
        assertTrue(userMailServiceMock.userEmails.contains(userGo.email))
    }

//    @Test
//    fun add() {
//        userPark.level = null
//        userService.add(userPark)
//        checkLevel(userPark, Level.BASIC)
//
//        val existingLevel = userLee.level!!
//        userService.add(userLee)
//        checkLevel(userLee, existingLevel)
//    }

    fun isLevelUpgradedFrom(prevUser: User, isUpgraded: Boolean) {
        val updatedUser = dao.get(prevUser.id)
        val prevLevel = prevUser.level
        val expectedLevel = if (isUpgraded) prevLevel.next else prevLevel
        assertEquals(expectedLevel, updatedUser.level)
    }
}