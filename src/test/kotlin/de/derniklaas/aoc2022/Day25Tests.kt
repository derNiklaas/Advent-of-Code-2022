package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day25Tests {
    private val day25 = Day25(readFile("Day25", true))

    @Test
    fun part1() {
        Assertions.assertEquals("2=-1=0", day25.part1())
    }
}