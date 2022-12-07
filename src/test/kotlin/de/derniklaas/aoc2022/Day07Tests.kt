package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day07Tests {
    private val day07 = Day07(AbstractFileType.fromInput(readFile("Day07", true)))

    @Test
    fun day() {
        Assertions.assertEquals(95437, day07.part1())
        Assertions.assertEquals(24933642, day07.part2())
    }
}