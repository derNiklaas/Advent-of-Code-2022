package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day06Tests {
    private val inputs = readFile("Day06", true)

    @Test
    fun part1() {
        Assertions.assertEquals(5, Day06(inputs[0]).part1())
        Assertions.assertEquals(6, Day06(inputs[1]).part1())
        Assertions.assertEquals(10, Day06(inputs[2]).part1())
        Assertions.assertEquals(11, Day06(inputs[3]).part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(19, Day06(inputs[4]).part2())
        Assertions.assertEquals(23, Day06(inputs[5]).part2())
        Assertions.assertEquals(23, Day06(inputs[6]).part2())
        Assertions.assertEquals(29, Day06(inputs[7]).part2())
        Assertions.assertEquals(26, Day06(inputs[8]).part2())
    }
}