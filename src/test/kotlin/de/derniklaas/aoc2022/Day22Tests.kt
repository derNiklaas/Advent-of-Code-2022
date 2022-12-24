package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day22Tests {
    private val input = readFile("Day22", true).joinToString("\n").split("\n\n").map { it.split("\n") }
    private val mappedSlope = input[0].map { line ->
        line.map {
            when (it) {
                ' ' -> Cell.CLOSE
                '.' -> Cell.OPEN
                '#' -> Cell.WALL
                else -> error("Unknown state $it")
            }
        }
    }
    private val mappedPath = input[1].first().let {
        val q = it.toCollection(ArrayDeque())

        buildList {
            while (q.isNotEmpty()) {
                val current = buildString {
                    val number = q.first().isDigit()
                    while (q.isNotEmpty() && q.first().isDigit() == number) {
                        append(q.removeFirst())
                    }
                }
                add(
                    current.toIntOrNull()?.let(::Distance) ?: when (current) {
                        "R" -> Rotation.CLOCKWISE
                        "L" -> Rotation.COUNTERCLOCKWISE
                        else -> error("Illegal rotation $current")
                    }
                )
            }
        }
    }
    private val day22 = Day22(mappedSlope, mappedPath)

    @Test
    fun part1() {
        Assertions.assertEquals(6032, day22.part1())
    }
}