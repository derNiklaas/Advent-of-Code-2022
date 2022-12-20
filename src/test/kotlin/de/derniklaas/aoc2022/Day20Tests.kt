package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day20Tests {
    private val input = readFile("Day20", true).withIndex().map { (index, line) -> index to line.toLong() }
    private val day20 = Day20(input)

    @Test
    fun part1() {
        Assertions.assertEquals(3, day20.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(1623178306, day20.part2())
    }
}