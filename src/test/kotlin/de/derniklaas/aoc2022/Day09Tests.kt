package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day09Tests {
    @Test
    fun part1() {
        val input = readFile("Day09A", true).map(MovementInstruction::fromInput)
        Assertions.assertEquals(13, Day09(input).part1())
    }

    @Test
    fun part2() {
        val input = readFile("Day09B", true).map(MovementInstruction::fromInput)
        Assertions.assertEquals(36, Day09(input).part2())
    }
}