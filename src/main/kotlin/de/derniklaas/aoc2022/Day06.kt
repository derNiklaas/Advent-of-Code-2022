package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day06").first()
    Day06(input).execute()
}

class Day06(private val input: String) : Day {
    override fun part1(): Any {
        for (i in 3 until input.length) {
            if (buildSet(i, 4).size == 4) {
                return i + 1
            }
        }
        error("No marker found")
    }

    override fun part2(): Any {
        for (i in 13 until input.length) {
            if (buildSet(i, 14).size == 14) {
                return i + 1
            }
        }
        error("No marker found")
    }

    private fun buildSet(index: Int, length: Int): Set<Char> =
        buildSet {
            for (j in 0 until length) {
                add(input[index - j])
            }
        }
}