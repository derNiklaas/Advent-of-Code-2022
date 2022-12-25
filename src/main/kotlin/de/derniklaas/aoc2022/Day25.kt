package de.derniklaas.aoc2022

import kotlin.math.pow
import kotlin.math.roundToInt

fun main() {
    val input = readFile("Day25")
    Day25(input).execute()
}

class Day25(private val input: List<String>) : Day {
    override fun part1(): String {
        val sum = input.sumOf { it.fromSNAFU() }
        return sum.toSNAFU()
    }

    override fun part2(): String = "Merry Christmas!"
}

private fun String.fromSNAFU(): Long {
    var sum = 0L

    for (i in this.length - 1 downTo 0) {
        sum += when (this[this.length - i - 1]) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> error("Unknown SNAFU Input ${this[i]}")
        }.toLong() * (5.0.pow(i).toLong())
    }
    return sum
}

private fun Long.toSNAFU(): String {
    var maxPow = 0
    while (5.toDouble().pow(maxPow + 1) < this) {
        maxPow++
    }
    var currentPow = maxPow
    var remaining = this.toDouble()
    var output = ""
    while (currentPow >= 0) {
        val power = 5.0.pow(currentPow)
        val symbol = (remaining / power).roundToInt()
        remaining -= symbol * power
        output += when (symbol) {
            -2 -> '='
            -1 -> '-'
            0 -> '0'
            1 -> '1'
            2 -> '2'
            else -> '?'
        }
        currentPow--
    }
    return output
}
