package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day10Tests {
    private val input = readFile("Day10", true).map(CPUInstruction::fromInput)
    private val day10 = Day10(input)

    @Test
    public fun day() {
        Assertions.assertEquals(13140, day10.part1())
        val correctOutput =
            "\n##..##..##..##..##..##..##..##..##..##..\n###...###...###...###...###...###...###.\n####....####....####....####....####....\n#####.....#####.....#####.....#####.....\n######......######......######......####\n#######.......#######.......#######.....\n"
        Assertions.assertEquals(correctOutput, day10.part2())
    }
}