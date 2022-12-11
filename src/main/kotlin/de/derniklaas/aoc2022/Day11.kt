package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day11").joinToString("\n").split("\n\n").map { it.split("\n").map(String::trim) }
        .map(Monkey::fromInput)
    Day11(input).execute()
}

class Day11(val input: List<Monkey>) : Day {

    private val commonDivider = input.map { it.testNumber }.reduce { acc: Long, elem: Long -> acc * elem }

    override fun part1(): Long = step(20)

    override fun part2(): Long = step(10000, false)

    private fun step(steps: Int, divide: Boolean = true): Long {
        val monkeys = mutableListOf<Monkey>()
        input.forEach { monkey -> monkeys += monkey.copy() }

        repeat(steps) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.itemsInspected++
                    var item = monkey.items.removeFirst()
                    item = monkey.operation(item)
                    if (divide) item /= 3
                    item %= this.commonDivider
                    if (item % monkey.testNumber == 0L) {
                        monkeys[monkey.trueMonkey].items += item
                    } else {
                        monkeys[monkey.falseMonkey].items += item
                    }
                }
            }
        }

        val sorted = monkeys.sortedByDescending { it.itemsInspected }.subList(0, 2).map { it.itemsInspected }
        return sorted[0].toLong() * sorted[1].toLong()
    }
}

class Monkey(
    startingItems: MutableList<Long>,
    val testNumber: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    val operation: (Long) -> Long
) {
    var items = startingItems
    var itemsInspected = 0

    companion object {
        fun fromInput(input: List<String>): Monkey {
            val items = input[1].replace("Starting items: ", "").splitAndMapToInt(", ").map(Int::toLong).toMutableList()
            val number = input[2].split(" ").last()
            val operation = if (input[2].contains("+")) { i: Long ->
                i + if (number == "old") i else number.toLong()
            } else { i: Long ->
                i * if (number == "old") i else number.toLong()
            }
            val testNumber = input[3].split(" ").last().toLong()
            val trueMonkey = input[4].split(" ").last().toInt()
            val falseMonkey = input[5].split(" ").last().toInt()

            return Monkey(items, testNumber, trueMonkey, falseMonkey, operation)
        }
    }

    fun copy(): Monkey {
        val items = mutableListOf<Long>()
        items.addAll(this.items)
        return Monkey(items, testNumber, trueMonkey, falseMonkey, operation)
    }
}