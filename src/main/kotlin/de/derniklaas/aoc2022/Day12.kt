package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day12")
    Day12(input).execute()
}

class Day12(val input: List<String>) : Day {

    private val field: List<String>
    private val startP1: MutableList<Pair<Int, Int>>
    private val startP2: MutableList<Pair<Int, Int>>
    private val end: Pair<Int, Int>

    init {
        startP1 = findCharacter(input, 'S')
        end = findCharacter(input, 'E').first()
        field = input.map { it.replace("S", "a").replace("E", "z") }
        startP2 = findCharacter(field, 'a')
    }


    override fun part1(): Int = breadthFirstSearch(field, startP1, end)

    override fun part2(): Int = breadthFirstSearch(field, startP2, end)

    private fun breadthFirstSearch(
        field: List<String>,
        queue: MutableList<Pair<Int, Int>>,
        target: Pair<Int, Int>
    ): Int {
        val distance = queue.associateWith { 0 }.toMutableMap()
        val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            directions
                .map { (x2, y2) -> x + x2 to y + y2 }
                .filter { (x2, y2) -> x2 in field.indices && y2 in field[0].indices }
                .filter { !distance.contains(it) }
                .filter { (x2, y2) -> field[x2][y2] <= field[x][y] + 1 }
                .forEach {
                    distance[it] = distance[Pair(x, y)]!! + 1
                    queue.add(it)
                }
        }
        return distance[target]!!
    }

    private fun findCharacter(field: List<String>, c: Char) = input
        .mapIndexed { x, l -> l.mapIndexed { y, _ -> x to y } }
        .flatten()
        .filter { (x, y) -> field[x][y] == c }
        .toMutableList()
}
