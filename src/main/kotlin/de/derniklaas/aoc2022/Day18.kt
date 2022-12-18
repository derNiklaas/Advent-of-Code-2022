package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day18").map {
        val (x, y, z) = it.split(",").mapToInt()
        Vec3D(x, y, z)
    }
    Day18(input).execute()
}

class Day18(private val input: List<Vec3D>) : Day {

    override fun part1(): Int = input.sumOf { countVisibleSides(it) }

    override fun part2(): Int {
        val minX = input.minOf { it.x } - 1
        val maxX = input.maxOf { it.x } + 1
        val minY = input.minOf { it.y } - 1
        val maxY = input.maxOf { it.y } + 1
        val minZ = input.minOf { it.z } - 1
        val maxZ = input.maxOf { it.z } + 1

        val queue = mutableListOf<Vec3D>()
        queue += Vec3D(minX, minY, minZ)
        val outside = mutableSetOf<Vec3D>()

        while (queue.isNotEmpty()) {
            for (neighbor in queue.removeFirst().getNeighbors()) {
                if (neighbor.x in minX..maxX && neighbor.y in minY..maxY && neighbor.z in minZ..maxZ && neighbor !in input) {
                    if (outside.add(neighbor)) {
                        queue += neighbor
                    }
                }
            }
        }

        return input.sumOf { it.getNeighbors().count { neighbor -> neighbor in outside } }
    }

    private fun countVisibleSides(cube: Vec3D): Int = cube.getNeighbors().count { it !in input }

}