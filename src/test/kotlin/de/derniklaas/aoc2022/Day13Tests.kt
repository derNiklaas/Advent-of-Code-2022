package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Tests {
    private val input = readFile("Day13", true).joinToString("\n").split("\n\n").map {
        it.split("\n").map(Packet::fromInput)
    }
    private val day13 = Day13(input)

    @Test
    fun part1() {
        Assertions.assertEquals(13, day13.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(140, day13.part2())
    }
}