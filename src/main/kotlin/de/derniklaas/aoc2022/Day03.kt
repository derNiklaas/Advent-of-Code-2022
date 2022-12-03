package de.derniklaas.aoc2022

fun main() {
    val input = Backpack.fromInput(readFile("Day03"))
    Day03(input).execute()
}

class Day03(private val input: List<Backpack>) : Day {
    override fun part1(): Int {
        return input.sumOf { it.firstHalf.removeOverlap(it.secondHalf).getPriority() }
    }

    override fun part2(): Int {
        return input.windowed(3, 3).sumOf {
            val (first, second, third) = it
            val firstBackpack = first.firstHalf + first.secondHalf
            val secondBackpack = second.firstHalf + second.secondHalf
            val thirdBackpack = third.firstHalf + third.secondHalf
            val overlap = firstBackpack.removeOverlap(secondBackpack, thirdBackpack)
            overlap.getPriority()
        }
    }
}

fun String.removeOverlap(other: String): Char {
    this.forEach {
        if (other.contains(it)) {
            return it
        }
    }

    other.forEach {
        if (this.contains(it)) {
            return it
        }
    }
    error("No overlap")
}

private fun String.removeOverlap(other: String, other2: String): Char {
    this.forEach {
        if (other.contains(it) && other2.contains(it)) {
            return it
        }
    }

    other.forEach {
        if (this.contains(it) && other2.contains(it)) {
            return it
        }
    }

    other2.forEach {
        if (other.contains(it) && this.contains(it)) {
            return it
        }
    }
    error("No overlap")
}

private fun Char.getPriority(): Int {
    return if (isUpperCase()) {
        this - 'A' + 27
    } else {
        this - 'a' + 1
    }
}

data class Backpack(val firstHalf: String, val secondHalf: String) {
    companion object {
        fun fromInput(input: List<String>): List<Backpack> = input.map {
            val length = it.length
            val firstHalf = it.substring(0 until length / 2)
            val secondHalf = it.substring(length / 2 until length)
            Backpack(firstHalf, secondHalf)
        }
    }
}
