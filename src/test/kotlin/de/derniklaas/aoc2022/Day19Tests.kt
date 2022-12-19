package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day19Tests {
    @Test
    fun part1() {
        val input = readFile("Day19", true).map(Blueprint::fromInput)
        Assertions.assertEquals(33, Day19(input).part1())
    }
}