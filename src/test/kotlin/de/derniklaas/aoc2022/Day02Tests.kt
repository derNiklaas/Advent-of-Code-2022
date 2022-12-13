package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day02Tests {
    private val day02 = Day02(Match.fromInput(readFile("Day02", true)))

    @Test
    fun part1() {
        Assertions.assertEquals(15, day02.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(12, day02.part2())
    }
}