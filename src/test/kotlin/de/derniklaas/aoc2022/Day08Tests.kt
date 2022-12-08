package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day08Tests {
    private val input = readFile("Day08", true).splitAndMapToInt("").mapIndexed { x, row ->
        row.mapIndexed { y, height ->
            Pair(x, y) to height
        }
    }.flatten().toMap()
    val day08 = Day08(input)

    @Test
    public fun part1() {
        Assertions.assertEquals(21, day08.part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals(8, day08.part2())
    }
}