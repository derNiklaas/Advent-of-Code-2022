package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day24Tests {
    private val input = readFile("Day24", true)
    private val dimensions = Vec2D(input.first().length - 2, input.size - 2)
    private val from = Vec2D(input.first().indexOf('.') - 1, -1)
    private val to = Vec2D(input.last().indexOf('.') - 1, dimensions.y)

    private val blizzards = buildSet {
        input[1 until input.size - 1].forEachIndexed { i, row ->
            row.substring(1 until row.length - 1).withIndex().filter { it.value != '.' }.forEach { (j, value) ->
                val direction = when (value) {
                    '>' -> Direction.RIGHT // 1, 0
                    '<' -> Direction.LEFT // -1, 0
                    '^' -> Direction.DOWN // 0, -1
                    'v' -> Direction.UP // 0, 1
                    else -> error("Illegal Input")
                }
                add(Vec2D(j, i) to direction)
            }
        }
    }
    private val day24 = Day24(blizzards, dimensions, from, to)

    @Test
    fun part1() {
        Assertions.assertEquals(18, day24.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(54, day24.part2())
    }
}