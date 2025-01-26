package com.hcpark.springbook.user.dao

import com.hcpark.springbook.user.domain.Level
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@SpringBootTest // h2 DB 실행을 위해 필요
class JdbcTemplateTest {

    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun test() {
        // given
        val template = JdbcTemplate(dataSource)
        template.update("insert into users values('1', '2', '3', ${Level.BASIC.value}, 0, 0, 'abc@gmail.com')")

        // when
        val count = template.queryForObject("select count(*) from users") { rs, _ -> rs.getInt(1) }

        // then
        assertEquals(1, count)
    }
}