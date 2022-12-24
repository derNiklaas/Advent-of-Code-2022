package de.derniklaas.aoc2022

fun main() {
    val (slope, path) = readFile("Day22").joinToString("\n").split("\n\n").map { it.split("\n") }
    val mappedSlope = slope.map { line ->
        line.map {
            when (it) {
                ' ' -> Cell.CLOSE
                '.' -> Cell.OPEN
                '#' -> Cell.WALL
                else -> error("Unknown state $it")
            }
        }
    }
    val mappedPath = path.first().let {
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

    Day22(mappedSlope, mappedPath).execute()


}

class Day22(private val slope: List<List<Cell>>, private val path: List<PathElement>) :
    Day {

    private val directions: List<Direction> = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)

    override fun part1(): Any {
        return calculatePath { currentPoint, currentFacing ->
            val localFacing = currentFacing.point * -1
            var localPoint = currentPoint.copy()

            while (slope.getOrNull(localPoint).let { it != null && it != Cell.CLOSE }) {
                localPoint += localFacing
            }

            (localPoint + currentFacing.point) to currentFacing
        }
    }

    private val side: Int = 50

    override fun part2(): Int {
        return calculatePath { currentPoint, currentFacing ->
            val (x, y) = currentPoint.dequadrantize()

            when (currentPoint.getQuadrant() to currentFacing) {
                1 to Direction.DOWN -> Vec2D(side - 1 - x, 0).quadrantize(6) to Direction.UP
                1 to Direction.LEFT -> Vec2D(y, 0).quadrantize(9) to Direction.UP
                2 to Direction.UP -> Vec2D(side - 1 - x, side - 1).quadrantize(7) to Direction.DOWN
                2 to Direction.RIGHT -> Vec2D(y, side - 1).quadrantize(4) to Direction.DOWN
                2 to Direction.LEFT -> Vec2D(side - 1, y).quadrantize(9) to Direction.LEFT
                4 to Direction.DOWN -> Vec2D(0, x).quadrantize(6) to Direction.RIGHT
                4 to Direction.UP -> Vec2D(side - 1, x).quadrantize(2) to Direction.LEFT
                6 to Direction.LEFT -> Vec2D(y, 0).quadrantize(4) to Direction.UP
                6 to Direction.DOWN -> Vec2D(side - 1 - x, 0).quadrantize(1) to Direction.UP
                7 to Direction.UP -> Vec2D(side - 1 - x, side - 1).quadrantize(2) to Direction.DOWN
                7 to Direction.RIGHT -> Vec2D(y, side - 1).quadrantize(9) to Direction.DOWN
                9 to Direction.DOWN -> Vec2D(0, x).quadrantize(1) to Direction.RIGHT
                9 to Direction.RIGHT -> Vec2D(0, y).quadrantize(2) to Direction.RIGHT
                9 to Direction.UP -> Vec2D(side - 1, x).quadrantize(7) to Direction.LEFT
                else -> error("Illegal state")
            }
        }
    }

    private fun calculatePath(wrappingRule: (Vec2D, Direction) -> Pair<Vec2D, Direction>): Int {
        var currentPoint = Vec2D(0, slope[0].indexOf(Cell.OPEN))
        var currentFacing = directions.first()
        path.forEach { element ->
            when (element) {
                is Rotation -> {
                    val ordinalDifference = when (element) {
                        Rotation.CLOCKWISE -> 1
                        Rotation.COUNTERCLOCKWISE -> -1
                    }

                    val index = directions.indexOf(currentFacing)
                    val newIndex = (index + ordinalDifference + directions.size) % directions.size
                    currentFacing = directions[newIndex]
                }

                is Distance -> {
                    repeat(element.distance) {
                        val newPoint = currentPoint + currentFacing.point

                        when (slope.getOrNull(newPoint)) {
                            Cell.WALL -> {}
                            Cell.OPEN -> currentPoint = newPoint
                            null, Cell.CLOSE -> wrappingRule(currentPoint, currentFacing).let {
                                if (slope[it.first] != Cell.WALL) {
                                    currentPoint = it.first
                                    currentFacing = it.second
                                }
                            }
                        }
                    }
                }
            }
        }
        return 1000 * (currentPoint.x + 1) + 4 * (currentPoint.y + 1) + directions.indexOf(currentFacing)
    }

    private fun Vec2D.getQuadrant(): Int = (x / side) * 3 + y / side

    private fun Vec2D.quadrantize(quadrant: Int) = Vec2D(x + quadrant / 3 * side, y + quadrant % 3 * side)

    private fun Vec2D.dequadrantize() = quadrantize(-getQuadrant())
}


enum class Cell {
    CLOSE,
    OPEN,
    WALL
}

enum class Direction(val point: Vec2D) {
    UP(Vec2D(0, 1)),
    DOWN(Vec2D(0, -1)),
    LEFT(Vec2D(-1, 0)),
    RIGHT(Vec2D(1, 0))
}

sealed interface PathElement

enum class Rotation : PathElement {
    CLOCKWISE,
    COUNTERCLOCKWISE
}

data class Distance(val distance: Int) : PathElement