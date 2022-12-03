package de.derniklaas.aoc2022

fun main() {
    val input = Backpack.fromInput(readFile("Day03"))
    Day03(input).execute()
}

class Day03(private val input: List<Backpack>) : Day {
    override fun part1(): Int {
        return input.sumOf {
            it.firstHalf.toSet().intersect(it.secondHalf.toSet()).first().getPriority()
        }
    }

    override fun part2(): Int {
        return input.windowed(3, 3).sumOf {
            val (first, second, third) = it
            val overlap = first.items.toSet().intersect(second.items.toSet()).intersect(third.items.toSet())
            overlap.first().getPriority()
        }
    }
}

private fun Char.getPriority(): Int {
    return if (isUpperCase()) {
        this - 'A' + 27
    } else {
        this - 'a' + 1
    }
}

data class Backpack(val items: String, val firstHalf: String, val secondHalf: String) {
    companion object {
        fun fromInput(input: List<String>): List<Backpack> = input.map {
            val length = it.length
            val firstHalf = it.substring(0 until length / 2)
            val secondHalf = it.substring(length / 2 until length)
            Backpack(it, firstHalf, secondHalf)
        }
    }
}
