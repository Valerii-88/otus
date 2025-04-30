package com.escodro.alkaa.test.utils

import kotlin.random.Random


object TestStringGenerator {

    private const val DEFAULT_CHARS = "abcdefghijklmnopqrstuvwxyz"
    private val random = Random(System.currentTimeMillis())


    fun generateRandomWord(length: Int, allowedChars: String = DEFAULT_CHARS): String {
        return (1..length)
            .map { allowedChars[random.nextInt(allowedChars.length)] }
            .joinToString("")
    }


    fun generateRandomSentence(wordCount: Int, maxWordLength: Int = 8): String {
        return (1..wordCount)
            .map { generateRandomWord(random.nextInt(3, maxWordLength)) }
            .joinToString(" ")
    }
}
