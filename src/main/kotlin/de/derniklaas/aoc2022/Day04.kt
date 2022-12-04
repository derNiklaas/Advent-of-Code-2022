package de.derniklaas.aoc2022

import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readFile("Day04").map {
        val (firstHalf, secondHalf) = it.split(",")
        val (first, second) = firstHalf.splitAndMapToInt("-")
        val (third, fourth) = secondHalf.splitAndMapToInt("-")
        first..second to third..fourth
    }
    Day04(input).execute()
}

class Day04(private val input: List<Pair<IntRange, IntRange>>) : Day {
    override fun part1(): Int {
        return input.count {
            it.first completelyOverlaps it.second || it.second completelyOverlaps it.first
        }
    }

    override fun part2(): Int {
        return input.count {
            it.first overlaps it.second
        }
    }
}

private infix fun IntRange.completelyOverlaps(other: IntRange): Boolean {
    return first <= other.first && last >= other.last
}

private infix fun IntRange.overlaps(other: IntRange): Boolean {
    val startingBoundary = max(first, other.first)
    val endingBoundary = min(last, other.last)
    return startingBoundary in this && startingBoundary in other &&
            endingBoundary in this && endingBoundary in other
}