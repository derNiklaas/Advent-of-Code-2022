package de.derniklaas.aoc2022

import kotlin.math.sqrt

fun main() {
    val input = readFile("Day08").splitAndMapToInt("").mapIndexed { x, row ->
        row.mapIndexed { y, height ->
            Pair(x, y) to height
        }
    }.flatten().toMap()
    Day08(input).execute()
}

class Day08(val input: Map<Pair<Int, Int>, Int>) : Day {
    override fun part1(): Any {
        val visible = mutableSetOf<Pair<Int, Int>>()
        val size = sqrt(input.size.toDouble()).toInt()

        // Add border trees
        visible.addAll(input.filter {
            it.key.first == 0 || it.key.first == size - 1 || it.key.second == 0 || it.key.second == size - 1
        }.map { it.key })

        for (i in 0 until size) {
            val column = input.filter { it.key.first == i }
            val row = input.filter { it.key.second == i }

            var highest = -1
            for (tree in column) {
                if (tree.value > highest) {
                    highest = tree.value
                    visible += tree.key
                }
            }
            highest = -1
            for (tree in column.entries.reversed()) {
                if (tree.value > highest) {
                    highest = tree.value
                    visible += tree.key
                }
            }
            highest = -1
            for (tree in row) {
                if (tree.value > highest) {
                    highest = tree.value
                    visible += tree.key
                }
            }
            highest = -1
            for (tree in row.entries.reversed()) {
                if (tree.value > highest) {
                    highest = tree.value
                    visible += tree.key
                }
            }
        }

        return visible.size
    }

    override fun part2(): Any {
        val viewingDistance = mutableMapOf<Pair<Int, Int>, Int>()
        for (tree in input) {
            var distance = 0
            var totalDistance = 1

            var position = tree.key.first + 1 to tree.key.second
            while (position in input) {
                distance++
                if (input[position]!! >= tree.value) break
                position = position.first + 1 to position.second
            }
            totalDistance *= distance
            distance = 0
            position = tree.key.first - 1 to tree.key.second
            while (position in input) {
                distance++
                if (input[position]!! >= tree.value) break
                position = position.first - 1 to position.second
            }
            totalDistance *= distance
            distance = 0
            position = tree.key.first to tree.key.second + 1
            while (position in input) {
                distance++
                if (input[position]!! >= tree.value) break
                position = position.first to position.second + 1
            }
            totalDistance *= distance
            distance = 0
            position = tree.key.first to tree.key.second - 1
            while (position in input) {
                distance++
                if (input[position]!! >= tree.value) break
                position = position.first to position.second - 1
            }
            totalDistance *= distance
            viewingDistance += tree.key to totalDistance
        }
        return viewingDistance.maxBy { it.value }.value
    }
}