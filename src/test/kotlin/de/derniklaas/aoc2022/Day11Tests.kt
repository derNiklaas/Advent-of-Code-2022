package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day11Tests {
    private val input =
        readFile("Day11", true).joinToString("\n").split("\n\n").map { it.split("\n").map(String::trim) }
            .map(Monkey::fromInput)
    private val day11 = Day11(input)

    @Test
    public fun part1() {
        Assertions.assertEquals(10605, day11.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(2713310158, day11.part2())
    }
}