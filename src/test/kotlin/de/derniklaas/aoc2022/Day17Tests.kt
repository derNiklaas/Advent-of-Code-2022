package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day17Tests {
    private val day17 = Day17(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")

    @Test
    fun part1() {
        Assertions.assertEquals(3068, day17.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(1514285714288, day17.part2())
    }
}