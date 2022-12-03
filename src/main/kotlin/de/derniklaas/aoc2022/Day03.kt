package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day03")
    Day03(input).execute()
}

class Day03(private val input: List<String>) : Day {
    override fun part1(): Int {
        return input.sumOf {
            val firstHalf = it.substring(0 until it.length / 2)
            val secondHalf = it.substring(it.length / 2 until it.length)
            (firstHalf intersect secondHalf).first().getPriority()
        }
    }

    override fun part2(): Int {
        return input.windowed(3, 3).sumOf {
            val (first, second, third) = it
            val overlap = first intersect second intersect third
            overlap.first().getPriority()
        }
    }
}

private infix fun String.intersect(other: String): String = this.toSet().intersect(other.toSet()).joinToString("")

private fun Char.getPriority(): Int {
    return if (isUpperCase()) {
        this - 'A' + 27
    } else {
        this - 'a' + 1
    }
}
