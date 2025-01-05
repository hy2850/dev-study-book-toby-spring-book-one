package com.hcpark.springbook.learningtest.templatecallback

import java.io.BufferedReader
import java.io.FileReader

class Calculator {

    fun calcSum(filepath: String?): Int {
        if (filepath == null) {
            throw IllegalArgumentException("Cannot find file")
        }

        BufferedReader(FileReader(filepath)).use { br ->
            var sum = 0
            var line: String?

            while (br.readLine().also { line = it } != null) {
                sum += Integer.parseInt(line)
            }

            return sum
        }
    }
}