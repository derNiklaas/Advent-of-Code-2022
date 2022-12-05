package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day05")
    val moves = TowerMove.fromInput(input)
    println("Part 1: ${Day05(Tower.fromInput(input), moves).part1()}")
    println("Part 2: ${Day05(Tower.fromInput(input), moves).part2()}")
}

class Day05(private val towers: List<Tower>, private val moves: List<TowerMove>) : Day {
    override fun part1(): String {
        moves.forEach {
            for (amount in 1..it.amount) {
                val fromTower = towers[it.from]
                val toTower = towers[it.to]
                val temp = fromTower.content[fromTower.height]
                fromTower.content[fromTower.height] = "-"
                toTower.content[toTower.height + 1] = temp
            }
        }
        return towers.joinToString("") { it.content[it.height] }
    }

    override fun part2(): String {
        moves.forEach {
            val fromTower = towers[it.from]
            val toTower = towers[it.to]
            val fromTowerHeight = fromTower.height
            for (i in it.amount - 1 downTo 0) {
                val temp = fromTower.content[fromTowerHeight - i]
                fromTower.content[fromTowerHeight - i] = "-"
                toTower.content[toTower.height + 1] = temp
            }
        }
        return towers.joinToString("") { it.content[it.height] }
    }
}

data class Tower(val content: MutableList<String>) {

    val height: Int
        get() {
            val index = content.indexOfFirst { it == "-" }
            return if (index == -1) content.size - 1 else index - 1
        }

    companion object {
        fun fromInput(input: List<String>): List<Tower> {
            val towers = mutableListOf<Tower>()

            val initialState = input.joinToString("\n").split("\n\n")[0].split("\n").dropLast(1).map {
                it.replace("    ", "[-] ").replace("[", "").replace("]", "").replace(" ", "")
            }.reversed()

            val length = initialState[0].length

            for (i in 0 until length) {
                val currentTower = mutableListOf<String>()
                initialState.forEach {
                    val char = if (it.length <= i) "-" else it[i].toString()
                    if (char != "-") {
                        currentTower.add(char)
                    }
                }
                for (j in 0..40) {
                    currentTower.add("-")
                }
                towers.add(Tower(currentTower))
            }

            return towers
        }
    }
}

data class TowerMove(val amount: Int, val from: Int, val to: Int) {
    companion object {
        fun fromInput(input: List<String>): List<TowerMove> {
            return input.joinToString("\n").split("\n\n")[1].split("\n").map {
                val splits = it.split(" ")
                val amount = splits[1].toInt()
                val from = splits[3].toInt() - 1
                val to = splits.last().toInt() - 1
                TowerMove(amount, from, to)
            }
        }
    }
}