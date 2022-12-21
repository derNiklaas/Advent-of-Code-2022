package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day21").map(SmartMonkey::fromInput)
    Day21(input).execute()
}

class Day21(private val input: List<SmartMonkey>) : Day {
    override fun part1(): Long {
        return getValue("root")
    }

    override fun part2(): String {
        val rootMonkey = input.find { it.name == "root" } as OperationMonkey
        val path = getPath("humn", listOf("root")).drop(1).reversed()
        println("Please use: https://www.dcode.fr/equation-solver")

        val equation = getEquation("x", path, path.drop(1))
        return if (rootMonkey.monkeyA in path) {
            "$equation = ${getValue(rootMonkey.monkeyB)}"
        } else {
            "$equation = ${getValue(rootMonkey.monkeyA)}"
        }
    }

    private fun getValue(name: String): Long {
        return when (val monkey = input.find { it.name == name }) {
            is NumberMonkey -> monkey.number
            is OperationMonkey -> {
                when (val operation = monkey.operation) {
                    "+" -> getValue(monkey.monkeyA) + getValue(monkey.monkeyB)
                    "-" -> getValue(monkey.monkeyA) - getValue(monkey.monkeyB)
                    "*" -> getValue(monkey.monkeyA) * getValue(monkey.monkeyB)
                    "/" -> getValue(monkey.monkeyA) / getValue(monkey.monkeyB)
                    else -> error("Illegal instruction $operation")
                }
            }

            else -> error("Should not happen")
        }
    }

    private fun getPath(name: String, path: List<String>): List<String> {
        val lastNode = path.last()
        val monkey = input.find { it.name == lastNode }
        if (monkey is OperationMonkey) {
            val pathA = getPath(name, path + monkey.monkeyA)
            if (pathA.contains(name)) return pathA
            val pathB = getPath(name, path + monkey.monkeyB)
            if (pathB.contains(name)) return pathB
        } else {
            if (monkey!!.name == name) return path
        }
        return emptyList()
    }

    private fun getEquation(equation: String, wholePath: List<String>, path: List<String>): String {
        if (path.isEmpty()) return equation
        val monkey = input.find { it.name == path.first() } as OperationMonkey
        val nextPath = path.drop(1)
        return if (monkey.monkeyA in wholePath) {
            getEquation("($equation)${monkey.operation}${getValue(monkey.monkeyB)}", wholePath, nextPath)
        } else {
            getEquation("${getValue(monkey.monkeyA)}${monkey.operation}($equation)", wholePath, nextPath)
        }
    }
}

sealed class SmartMonkey(val name: String) {

    companion object {
        fun fromInput(input: String): SmartMonkey {
            val splits = input.split(" ")
            val name = splits[0].replace(":", "")
            return if (splits.size == 2) {
                NumberMonkey(name, splits[1].toLong())
            } else {
                OperationMonkey(name, splits[1], splits[3], splits[2])
            }
        }
    }
}

class NumberMonkey(name: String, val number: Long) : SmartMonkey(name)

class OperationMonkey(name: String, val monkeyA: String, val monkeyB: String, val operation: String) : SmartMonkey(name)
