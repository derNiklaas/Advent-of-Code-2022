package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01Tests {

    @Test
    public fun part1() {
        val input = readFile("Day01", true)
        Assertions.assertEquals(24000, Day01(Basket.fromInput(input)).part1())
    }

    @Test
    public fun part2() {
        val input = readFile("Day01", true)
        Assertions.assertEquals(45000, Day01(Basket.fromInput(input)).part2())
    }
}