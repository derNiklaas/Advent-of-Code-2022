package de.derniklaas.aoc2022

import java.util.PriorityQueue
import kotlin.math.absoluteValue

fun main() {
    val input = readFile("Day24")
    val dimensions = Vec2D(input.first().length - 2, input.size - 2)
    val from = Vec2D(input.first().indexOf('.') - 1, -1)
    val to = Vec2D(input.last().indexOf('.') - 1, dimensions.y)

    val blizzards = buildSet {
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

    Day24(blizzards, dimensions, from, to).execute()
}

class Day24(
    private val originalBlizzards: Set<Pair<Vec2D, Direction>>,
    private val dimensions: Vec2D,
    private val from: Vec2D,
    private val to: Vec2D
) : Day {

    override fun part1(): Int = simulate(originalBlizzards, dimensions, from, to)
    override fun part2(): Any {
        val initialRun = simulate(originalBlizzards, dimensions, from, to)
        val secondRun = simulate(originalBlizzards, dimensions, to, from, initialRun)
        return simulate(originalBlizzards, dimensions, from, to, secondRun)
    }

    private val blizzards = mutableMapOf<Int, Set<Vec2D>>()

    private fun simulate(
        originalBlizzards: Set<Pair<Vec2D, Direction>>, dimensions: Vec2D, from: Vec2D, to: Vec2D, initialTime: Int = 0
    ): Int {
        val positions = PriorityQueue(compareBy<Pair<Vec2D, Int>> { (point, iteration) ->
            (to - point).let { it.x.absoluteValue + it.y.absoluteValue } + iteration * 10
        })

        positions += from to initialTime + 1

        val used = mutableSetOf<Pair<Vec2D, Int>>()

        while (true) {
            val currentPoint = positions.remove()
            val currentBlizzards = blizzards.getOrPut(currentPoint.second) {
                originalBlizzards.map { (point, direction) ->
                    val newPoint = point + direction.point * currentPoint.second
                    Vec2D(newPoint.x within dimensions.x, newPoint.y within dimensions.y)
                }.toSet()
            }

            Direction.values()
                .map { it.point }
                .plus(Vec2D(0, 0))
                .map { it + currentPoint.first }
                .filter { it.x in 0 until dimensions.x && (it.y in 0 until dimensions.y || it == from || it == to) }
                .filter { it !in currentBlizzards }
                .map { it to currentPoint.second + 1 }
                .filter { it !in used }
                .forEach {
                    if (it.first == to) return currentPoint.second
                    positions += it
                    used += it
                }
        }
    }
}
