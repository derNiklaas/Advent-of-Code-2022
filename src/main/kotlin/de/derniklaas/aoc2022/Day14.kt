package de.derniklaas.aoc2022

import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readFile("Day14").map {
        it.split(" -> ").windowed(2).map { (a, b) ->
            val (ax, ay) = a.splitAndMapToInt(",")
            val (bx, by) = b.splitAndMapToInt(",")
            min(ax, bx)..max(ax, bx) to min(ay, by)..max(ay, by)
        }
    }.flatten()
    Day14(input).execute()
}

class Day14(val input: List<Pair<IntRange, IntRange>>) : Day {

    private val maxY = input.maxOf { it.second.first }

    private val down = Vec2D(0, 1)
    private val downLeft = Vec2D(-1, 1)
    private val downRight = Vec2D(1, 1)

    override fun part1(): Int = simulate(input) { it.y <= maxY }

    override fun part2(): Int {
        val floor = Pair(Int.MIN_VALUE..Int.MAX_VALUE, maxY + 2..maxY + 2)
        val fixedInput = input.toMutableList()
        fixedInput.add(floor)

        return simulate(fixedInput) { true }
    }

    private fun simulate(map: List<Pair<IntRange, IntRange>>, check: (Vec2D) -> Boolean): Int {
        val sand = mutableSetOf<Vec2D>()
        var currentSandLocation = Vec2D(500, 0)

        while (check(currentSandLocation) && Vec2D(500, 0) !in sand) {
            if (!isOccupied(map, sand, currentSandLocation + down)) {
                currentSandLocation += down
            } else if (!isOccupied(map, sand, currentSandLocation + downLeft)) {
                currentSandLocation += downLeft
            } else if (!isOccupied(map, sand, currentSandLocation + downRight)) {
                currentSandLocation += downRight
            } else {
                sand += currentSandLocation
                currentSandLocation = Vec2D(500, 0)
            }
        }
        return sand.size
    }

    private fun isOccupied(map: List<Pair<IntRange, IntRange>>, sand: Set<Vec2D>, position: Vec2D): Boolean {
        val hitWall = map.any { (allX, allY) ->
            position.x in allX && position.y in allY
        }
        return hitWall || position in sand
    }
}
