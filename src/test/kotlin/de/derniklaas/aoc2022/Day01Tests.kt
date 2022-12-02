package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01Tests {
    private val day01 = Day01(Basket.fromInput(readFile("Day01", true)))

    @Test
    public fun part1() {
        Assertions.assertEquals(24000, day01.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(45000, day01.part2())
    }
}