package com.hcpark.springbook.learningtest.templatecallback

import java.io.BufferedReader
import java.io.FileReader

class Calculator {

    fun calcSum(filepath: String?): Int {
        if (filepath == null) {
            throw IllegalArgumentException("Cannot find file")
        }

        val br = BufferedReader(FileReader(filepath))

        var sum = 0
        var line: String?

        while (br.readLine().also { line = it } != null) {
            sum += Integer.parseInt(line)
        }

        br.close()
        return sum
    }
}