package com.hcpark.springbook.learningtest.templatecallback

fun interface NumberReaderCallback {

    fun handleNumber(line: String, accumulatedNum: Int): Int
}