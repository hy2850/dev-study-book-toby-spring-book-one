package com.hcpark.springbook

import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.Test

@SpringBootTest
class SprintContextTest {

    @Autowired
    private lateinit var ctx: ApplicationContext

    companion object {
        private var testObjects: MutableSet<Int> = mutableSetOf()
        private var previousCtx: ApplicationContext? = null
    }

    @Test
    fun testInstanceCreation1() {
        val currentCtxHashCode = ctx.hashCode()

        if (previousCtx == null) {
            previousCtx = ctx
            testObjects.add(currentCtxHashCode)
            return
        }

        assertEquals(1, testObjects.size)

        if (!testObjects.contains(currentCtxHashCode)) {
            throw AssertionError("Application context should be same")
        }
    }

    @Test
    fun testInstanceCreation2() {
        val currentCtxHashCode = ctx.hashCode()

        if (previousCtx == null) {
            previousCtx = ctx
            testObjects.add(currentCtxHashCode)
            return
        }

        assertEquals(1, testObjects.size)

        if (!testObjects.contains(currentCtxHashCode)) {
            throw AssertionError("Application context should be same")
        }
    }

    @Test
    fun testInstanceCreation3() {
        val currentCtxHashCode = ctx.hashCode()

        if (previousCtx == null) {
            previousCtx = ctx
            testObjects.add(currentCtxHashCode)
            return
        }

        assertEquals(1, testObjects.size)

        if (!testObjects.contains(currentCtxHashCode)) {
            throw AssertionError("Application context should be same")
        }
    }
}