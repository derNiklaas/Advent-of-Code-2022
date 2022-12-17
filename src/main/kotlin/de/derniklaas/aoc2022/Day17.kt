package de.derniklaas.aoc2022

fun main() {
    Day17(readFile("Day17").first()).execute()
}

class Day17(val input: String) : Day {

    private val heightHistory = mutableListOf<Int>()
    override fun part1(): Int = simulate(2022)

    override fun part2(): Long {
        // Generate a long good history data to find loops
        simulate(2022 * 5, true)
        val diffHistory = heightHistory.zipWithNext().map { (a, b) -> b - a }

        // Start at a later index to have a "better" sample
        val startIndex = 200
        val markerLength = 100
        val marker = diffHistory.subList(startIndex, startIndex + markerLength)

        var height = -1
        var loopLength = -1
        val heightBeforeLoop = heightHistory[startIndex - 1]
        for (i in startIndex + markerLength until diffHistory.size) {
            if (marker == diffHistory.subList(i, i + markerLength)) {
                loopLength = i - startIndex
                height = heightHistory[i - 1] - heightBeforeLoop
                break
            }
        }

        val loops = (1_000_000_000_000 - startIndex) / loopLength
        val offsetLastLoop = ((1_000_000_000_000 - startIndex) % loopLength).toInt()
        val offsetHeight = heightHistory[startIndex + offsetLastLoop] - heightBeforeLoop

        return heightBeforeLoop + height * loops + offsetHeight - 1 // Love "off by one" errors
    }

    private fun simulate(end: Int, record: Boolean = false): Int {
        val staticBlocks = mutableSetOf<Vec2D>()
        val validX = 0..6

        // Floor starts at y=0, first "real" layer is y=1
        validX.forEach {
            staticBlocks.add(Vec2D(it, 0))
        }
        var highestY = 0
        var move = 0
        var blocks = 0
        var anchor = Vec2D(2, 4)

        val left = Vec2D(-1, 0) // <
        val right = Vec2D(1, 0) // >
        val down = Vec2D(0, -1)

        while (blocks != end) {
            var blockLocations = Shape.values()[blocks % 5].offsetsFromAnchor.map { anchor + it }
            //printBottom(anchor, Shape.values()[blocks % 5], staticBlocks, highestY)

            val direction = when (input[move % input.length]) {
                '<' -> left
                '>' -> right
                else -> error("Unknown direction")
            }
            move++
            // Attempt to move the shape in a direction
            val movedBlocks = blockLocations.map { it + direction }
            if (movedBlocks.none { it.x !in validX || it in staticBlocks }) {
                anchor += direction
                blockLocations = blockLocations.map { it + direction }
                //println("Moved $direction to $anchor")
            }

            // Attempt to move the blocks down or "freeze" the rocks
            val downLocations = blockLocations.map { it + down }
            if (downLocations.any { it in staticBlocks }) {
                staticBlocks.addAll(blockLocations)
                highestY = staticBlocks.maxOf { it.y }
                if (record) heightHistory.add(highestY)
                //printBottom(staticBlocks, highestY)
                anchor = Vec2D(2, highestY + 4)
                blocks++
            } else {
                anchor += down
            }
        }

        return highestY
    }
}

enum class Shape(val offsetsFromAnchor: Set<Vec2D>) {
    DASH(setOf(Vec2D(0, 0), Vec2D(1, 0), Vec2D(2, 0), Vec2D(3, 0))),
    PLUS(setOf(Vec2D(1, 2), Vec2D(0, 1), Vec2D(1, 1), Vec2D(2, 1), Vec2D(1, 0))),
    L(setOf(Vec2D(2, 2), Vec2D(2, 1), Vec2D(0, 0), Vec2D(1, 0), Vec2D(2, 0))),
    PIPE(setOf(Vec2D(0, 0), Vec2D(0, 1), Vec2D(0, 2), Vec2D(0, 3))),
    CUBE(setOf(Vec2D(0, 0), Vec2D(1, 0), Vec2D(0, 1), Vec2D(1, 1)))
}