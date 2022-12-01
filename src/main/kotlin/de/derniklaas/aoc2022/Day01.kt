package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day01")
    Day01(Basket.fromInput(input)).execute()
}

public class Day01(private val input: List<Basket>) : Day {
    override fun part1(): Int {
        return input.maxOf { it.calories }
    }

    override fun part2(): Int {
        val sorted = input.sortedByDescending { it.calories }
        return sorted[0].calories + sorted[1].calories + sorted[2].calories
    }
}

data class Basket(val calories: Int) {
    companion object {
        public fun fromInput(input: List<String>): List<Basket> {
            val baskets = ArrayList<Basket>()
            var sum = 0;
            for (line in input) {
                if (line.isEmpty()) {
                    baskets += Basket(sum)
                    sum = 0;
                } else {
                    sum += line.toInt();
                }
            }
            baskets += Basket(sum)
            return baskets;
        }
    }
}