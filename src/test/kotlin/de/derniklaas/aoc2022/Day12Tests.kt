package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day12Tests {
    private val day12 = Day12(readFile("Day12", true))

    @Test
    public fun part1() {
        Assertions.assertEquals(31, day12.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(29, day12.part2())
    }
}