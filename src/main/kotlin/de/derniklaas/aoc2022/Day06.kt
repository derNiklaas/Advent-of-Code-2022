package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day06").first()
    Day06(input).execute()
}

class Day06(private val input: String) : Day {
    val set = hashSetOf<Char>()
    override fun part1(): Int {
        return input.windowedSequence(4).indexOfFirst {
            set.clear()
            it.all { char -> set.add(char) }
        } + 4
    }

    override fun part2(): Int {
        return input.windowedSequence(14).indexOfFirst {
            set.clear()
            it.all { char -> set.add(char) }
        } + 14
    }
}