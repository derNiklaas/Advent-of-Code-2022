package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day23Tests {
    private val input = buildSet {
        readFile("Day23", true).forEachIndexed { i, row ->
            row.withIndex().filter { it.value == '#' }.forEach { add(Vec2D(i, it.index)) }
        }
    }
    private val day23 = Day23(input)

    @Test
    fun part1() {
        Assertions.assertEquals(110, day23.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(20, day23.part2())
    }
}