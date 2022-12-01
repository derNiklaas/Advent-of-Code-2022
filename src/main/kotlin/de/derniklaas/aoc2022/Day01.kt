package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day01")
    Day01(Basket.fromInput(input)).execute()
}

public class Day01(input: List<Basket>) : Day {

    private val sorted = input.sortedByDescending { it.calories }

    override fun part1(): Int {
        return sorted.first().calories
    }

    override fun part2(): Int {
        return sorted.subList(0, 3).sumOf { it.calories }
    }
}

data class Basket(val calories: Int) {
    companion object {
        public fun fromInput(input: List<String>): List<Basket> {
            val baskets = ArrayList<Basket>()
            var sum = 0
            for (line in input) {
                if (line.isEmpty()) {
                    baskets += Basket(sum)
                    sum = 0
                } else {
                    sum += line.toInt()
                }
            }
            baskets += Basket(sum)
            return baskets
        }
    }
}