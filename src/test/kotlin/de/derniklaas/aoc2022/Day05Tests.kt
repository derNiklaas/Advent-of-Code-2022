package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day05Tests {

    private val input = readFile("Day05", true)
    private val moves = TowerMove.fromInput(input)

    @Test
    public fun part1() {
        Assertions.assertEquals("CMZ", Day05(Tower.fromInput(input), moves).part1())
    }

    @Test
    public fun part2() {
        Assertions.assertEquals("MCD", Day05(Tower.fromInput(input), moves).part2())
    }
}