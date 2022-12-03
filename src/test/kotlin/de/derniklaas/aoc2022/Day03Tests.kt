package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day03Tests {
    private val day03 = Day03(readFile("Day03", true))

    @Test
    public fun part1() {
        Assertions.assertEquals(157, day03.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(70, day03.part2())
    }
}