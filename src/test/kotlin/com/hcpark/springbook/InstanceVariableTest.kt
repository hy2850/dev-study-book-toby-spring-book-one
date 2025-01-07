package com.hcpark.springbook

import jakarta.annotation.PostConstruct
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.fail

// 인스턴스 변수는 매 @Test 마다 초기화, 스태틱 변수는 유지 (공유됨) 테스트
class InstanceVariableTest {

    private var instanceVar: MutableList<Int> = mutableListOf()

    companion object {
        private var staticVar: MutableList<Int> = mutableListOf()
    }

    @PostConstruct
    fun init() {
        fail("failed") // doesn't work
        println("hello") // doesn't print
    }

    @Test
    fun test1() {
        assertTrue(instanceVar.isEmpty())
        if(staticVar.isNotEmpty()) assertTrue(true)

        instanceVar.add(1)
        staticVar.add(1)

        println(instanceVar)
        println(staticVar)
    }

    @Test
    fun test2() {
        assertTrue(instanceVar.isEmpty())
        if(staticVar.isNotEmpty()) assertTrue(true)

        instanceVar.add(2)
        staticVar.add(2)

        println(instanceVar)
        println(staticVar)
    }

    @Test
    fun test3() {
        assertTrue(instanceVar.isEmpty())
        if(staticVar.isNotEmpty()) assertTrue(true)

        instanceVar.add(3)
        staticVar.add(3)

        println(instanceVar)
        println(staticVar)
    }
}