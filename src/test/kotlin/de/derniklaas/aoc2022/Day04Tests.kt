package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day04Tests {
    val input = readFile("Day04", true).map {
        val (firstHalf, secondHalf) = it.split(",")
        val (first, second) = firstHalf.splitAndMapToInt("-")
        val (third, fourth) = secondHalf.splitAndMapToInt("-")
        first..second to third..fourth
    }
    private val day04 = Day04(input)

    @Test
    public fun part1() {
        Assertions.assertEquals(2, day04.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(4, day04.part2())
    }
}