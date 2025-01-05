package com.hcpark.springbook.learningtest.templatecallback

import java.io.BufferedReader
import java.io.FileReader

class Calculator {

    fun calcContext(filepath: String?, initVal: Int, callback: NumberReaderCallback): Int {
        if (filepath == null) {
            throw IllegalArgumentException("Cannot find file")
        }

        BufferedReader(FileReader(filepath)).use { br ->
            var acc = initVal
            var line: String?

            while (br.readLine().also { line = it } != null) {
                acc = callback.handleNumber(line!!, acc)
            }

            return acc
        }
    }

    fun calcSum(filepath: String?): Int {
        return calcContext(filepath, 0) { line, sum ->
            sum + Integer.parseInt(line)
        }
    }

    fun calcMultiply(filepath: String?): Int {
        return calcContext(filepath, 1) { line, mul ->
            mul * Integer.parseInt(line)
        }
    }
}