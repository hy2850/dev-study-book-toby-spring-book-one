package com.hcpark.springbook.user.service

import com.hcpark.springbook.user.dao.UserDao
import com.hcpark.springbook.user.dao.UserDaoMock
import com.hcpark.springbook.user.domain.Level
import com.hcpark.springbook.user.domain.User
import com.hcpark.springbook.user.service.TestExceptionUserServiceMock.TestUserServiceException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.MailSender
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.PlatformTransactionManager
import kotlin.test.Test

@SpringBootTest
class UserServiceTest {

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
        userService.upgradeAllLevels()

        isLevelUpgradedFrom(userPark, false)
        isLevelUpgradedFrom(userKim, true)
        isLevelUpgradedFrom(userLee, false)
        isLevelUpgradedFrom(userGo, true)
        isLevelUpgradedFrom(userKwon, false)
    }

    @Test
    fun upgradeAllLevels_exception() {
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

        assertEquals(userDaoMock.users.size, userDaoMock.updatedUsers.size)

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