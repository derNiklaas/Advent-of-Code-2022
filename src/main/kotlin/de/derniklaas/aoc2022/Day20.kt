package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day20").withIndex().map { (index, line) -> index to line.toLong() }
    Day20(input).execute()
}

class Day20(private val input: List<Pair<Int, Long>>) : Day {
    override fun part1(): Long = shuffleNumbers(input)

    override fun part2(): Long = shuffleNumbers(input.map { it.first to it.second * 811589153L }, 10)

    private fun shuffleNumbers(numbers: List<Pair<Int, Long>>, repeats: Int = 1): Long {
        val copy = numbers.toMutableList()

        repeat(repeats) {
            copy.indices.forEach { index ->
                val currIndex = copy.indexOfFirst { it.first == index }
                val value = copy[currIndex]
                copy.removeAt(currIndex)
                copy.add((currIndex + value.second).mod(copy.size), value)
            }
        }

        val zero = copy.indexOfFirst { it.second == 0L }
        return copy[(zero + 1000) % copy.size].second + copy[(zero + 2000) % copy.size].second + copy[(zero + 3000) % copy.size].second
    }

}