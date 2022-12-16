package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day16Tests {
    private val day16 = Day16(readFile("Day16", true).map(Valve::fromInput))

    @Test
    fun part1() {
        Assertions.assertEquals(1651, day16.part1())
    }

    /*
    @Test
    fun part2() {
        Assertions.assertEquals(1707, day16.part2())
    }
    */
}