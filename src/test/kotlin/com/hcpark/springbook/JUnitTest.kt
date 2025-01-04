package com.hcpark.springbook

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class JUnitTest {

    // 매 @Test마다 새로운 테스트 클래스 인스턴스가 생성되는지 TC

    companion object {
        private var previousInstanceHashCode: Int? = null
    }

    @Test
    fun testInstanceCreation1() {
        val currentInstanceHashCode = this.hashCode()
        if (previousInstanceHashCode != null) {
            println("previousInstanceHashCode: $previousInstanceHashCode, currentInstanceHashCode: $currentInstanceHashCode")
            assertNotEquals(previousInstanceHashCode, currentInstanceHashCode, "Test class instance should be different")
        }
        previousInstanceHashCode = currentInstanceHashCode
    }

    @Test
    fun testInstanceCreation2() {
        val currentInstanceHashCode = this.hashCode()
        if (previousInstanceHashCode != null) {
            println("previousInstanceHashCode: $previousInstanceHashCode, currentInstanceHashCode: $currentInstanceHashCode")
            assertNotEquals(previousInstanceHashCode, currentInstanceHashCode, "Test class instance should be different")
        }
        previousInstanceHashCode = currentInstanceHashCode
    }

    @Test
    fun testInstanceCreation3() {
        val currentInstanceHashCode = this.hashCode()
        if (previousInstanceHashCode != null) {
            println("previousInstanceHashCode: $previousInstanceHashCode, currentInstanceHashCode: $currentInstanceHashCode")
            assertNotEquals(previousInstanceHashCode, currentInstanceHashCode, "Test class instance should be different")
        }
        previousInstanceHashCode = currentInstanceHashCode
    }
}