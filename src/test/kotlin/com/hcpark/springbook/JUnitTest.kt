package com.hcpark.springbook

import org.junit.jupiter.api.Test

// 매 @Test마다 새로운 테스트 클래스 인스턴스가 생성되는지 TC
class JUnitTest {

    companion object {
        private var testObjects: MutableSet<Int> = HashSet()
    }

    @Test
    fun testInstanceCreation1() {
        val currentInstanceHashCode = this.hashCode()
        if (testObjects.contains(currentInstanceHashCode)) {
            throw AssertionError("Test class instance should be different")
        }
        testObjects.add(currentInstanceHashCode)
    }

    @Test
    fun testInstanceCreation2() {
        val currentInstanceHashCode = this.hashCode()
        if (testObjects.contains(currentInstanceHashCode)) {
            throw AssertionError("Test class instance should be different")
        }
        testObjects.add(currentInstanceHashCode)
    }

    @Test
    fun testInstanceCreation3() {
        val currentInstanceHashCode = this.hashCode()
        if (testObjects.contains(currentInstanceHashCode)) {
            throw AssertionError("Test class instance should be different")
        }
        testObjects.add(currentInstanceHashCode)
    }
}