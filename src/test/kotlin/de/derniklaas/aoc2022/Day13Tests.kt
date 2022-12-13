package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day13Tests {
    private val input = readFile("Day13", true).joinToString("\n").split("\n\n").map {
        it.split("\n").map(Packet::fromInput)
    }
    private val day13 = Day13(input)

    @Test
    public fun part1() {
        Assertions.assertEquals(31, day13.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(29, day13.part2())
    }
}