package de.derniklaas.aoc2022

fun main() {
    val input = buildSet {
        readFile("Day23").forEachIndexed { i, row ->
            row.withIndex().filter { it.value == '#' }.forEach { add(Vec2D(i, it.index)) }
        }
    }
    Day23(input).execute()
}

class Day23(private val input: Set<Vec2D>) : Day {

    private val moves = listOf(
        Direction.LEFT to (-1..1).map { Vec2D(-1, it) },
        Direction.RIGHT to (-1..1).map { Vec2D(1, it) },
        Direction.DOWN to (-1..1).map { Vec2D(it, -1) },
        Direction.UP to (-1..1).map { Vec2D(it, 1) },
    )

    override fun part1(): Any {
        val tiles = input.toMutableSet()
        repeat(10) { wander(tiles, it % moves.size) }

        val minX = tiles.minOf { it.x }
        val maxX = tiles.maxOf { it.x }
        val minY = tiles.minOf { it.y }
        val maxY = tiles.maxOf { it.y }

        return (minX..maxX).sumOf { i ->
            (minY..maxY).count { Vec2D(i, it) !in tiles }
        }
    }

    override fun part2(): Any {
        val tiles = input.toMutableSet()
        repeat(Int.MAX_VALUE) {
            val lastIteration = tiles.toSet()
            wander(tiles, it % moves.size)
            if (tiles == lastIteration) return it + 1
        }
        error("it broke.")
    }

    private fun wander(tiles: MutableSet<Vec2D>, move: Int) {
        tiles.asSequence().filter { point ->
            moves.any { neighbours -> neighbours.second.any { point + it in tiles } }
        }.mapNotNull { point ->
            moves[move until moves.size].asSequence()
                .plus(moves[0 until move])
                .find { (_, scope) -> scope.none { point + it in tiles } }
                ?.let { point + it.first.point to point }
        }.groupBy({ it.first }) { it.second }
            .asSequence()
            .filter { it.value.size == 1 }
            .forEach { (to, from) ->
                tiles -= from.single()
                tiles += to
            }
    }
}