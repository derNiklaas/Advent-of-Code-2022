package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Tests {
    private val input = readFile("Day18").map {
        val (x, y, z) = it.split(",").mapToInt()
        Vec3D(x, y, z)
    }
    private val day18 = Day18(input)

    @Test
    fun part1() {
        Assertions.assertEquals(64, day18.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(58, day18.part2())
    }
}