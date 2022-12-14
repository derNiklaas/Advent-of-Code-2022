package de.derniklaas.aoc2022

import kotlin.math.max
import kotlin.math.min
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Tests {
    private val input = readFile("Day14", true).map {
        it.split(" -> ").windowed(2).map { (a, b) ->
            val (ax, ay) = a.splitAndMapToInt(",")
            val (bx, by) = b.splitAndMapToInt(",")
            min(ax, bx)..max(ax, bx) to min(ay, by)..max(ay, by)
        }
    }.flatten()
    private val day14 = Day14(input)

    @Test
    fun part1() {
        Assertions.assertEquals(24, day14.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(93, day14.part2())
    }
}