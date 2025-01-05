package com.hcpark.springbook.learningtest.templatecallback

fun interface LineReaderCallback<T> {

    fun handleLine(line: String, acc: T): T
}