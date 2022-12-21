package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day21Tests {
    private val day21 = Day21(readFile("Day21", true).map(SmartMonkey::fromInput))

    @Test
    fun part1() {
        Assertions.assertEquals(152, day21.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals("(4+(2*((x)-3)))/4 = 150", day21.part2())
    }
}