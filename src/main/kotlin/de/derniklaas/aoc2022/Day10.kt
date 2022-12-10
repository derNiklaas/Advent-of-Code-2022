package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day10").map(CPUInstruction::fromInput)
    Day10(input).execute()
}

class Day10(val input: List<CPUInstruction>) : Day {
    private val x = mutableMapOf(0 to 1, 1 to 1)
    override fun part1(): Any {
        var cycle = 1
        for (instruction in input) {
            val currentX = x[cycle] ?: x[cycle - 1] ?: 1
            when (instruction) {
                is NoopInstruction -> {
                    x[cycle] = currentX
                    cycle++
                }

                is AddInstruction -> {
                    x += cycle to currentX
                    cycle++
                    val updatedX = x[cycle] ?: x[cycle - 1]!!
                    x += cycle to updatedX
                    cycle++
                    x += cycle to x[cycle - 1]!! + instruction.amount
                }
            }
        }
        var sum = 0
        for (i in 20 until x.size - 1 step 40) {
            sum += x[i]!! * i
        }
        return sum
    }

    override fun part2(): String {
        var output = "\n"
        for (j in 0 until 6) {
            for (i in 1..40) {
                val xValue = x[40 * j + i]!!
                output += if (i in xValue..xValue + 2) "#" else "."
            }
            output += "\n"
        }
        return output
    }


}

sealed class CPUInstruction {
    companion object {
        fun fromInput(input: String): CPUInstruction {
            val splits = input.split(" ")
            return when (splits[0]) {
                "noop" -> NoopInstruction
                "addx" -> AddInstruction(splits[1].toInt())
                else -> error("Unknown instruction ${splits[0]}")
            }
        }
    }
}

object NoopInstruction : CPUInstruction()

data class AddInstruction(val amount: Int) : CPUInstruction()