package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day16").map(Valve::fromInput)
    Day16(input).execute()
}

class Day16(val input: List<Valve>) : Day {

    private val connections = input.associateWith { valve -> input.filter { it.name in valve.leadsTo } }
    private val initial = input.first { it.name == "AA" }
    private val seen = hashMapOf<Travel, Int>()

    override fun part1(): Int = solve(Travel(30, setOf(), initial))

    override fun part2(): Int = solve(Travel(26, setOf(), initial, true))

    private fun solve(travel: Travel): Int {
        if (travel.timeLeft <= 0) {
            return if (travel.partTwo)
                solve(travel.copy(timeLeft = 26, position = initial, partTwo = false))
            else 0
        }
        if (travel in seen) return seen[travel]!!
        var result = 0

        connections.getValue(travel.position).forEach {
            result = maxOf(result, solve(travel.copy(position = it, timeLeft = travel.timeLeft - 1)))
        }

        if (travel.position.flowRate > 0 && travel.position !in travel.opened) {
            result = maxOf(
                result, solve(
                    travel.copy(
                        opened = travel.opened + travel.position, timeLeft = travel.timeLeft - 1
                    )
                ) + (travel.timeLeft - 1) * travel.position.flowRate
            )
        }
        seen[travel] = result
        return result
    }
}

data class Valve(val name: String, val flowRate: Int, val leadsTo: Set<String>) {
    companion object {
        fun fromInput(input: String): Valve {
            val splits = input.split(" ")
            val name = splits[1]
            val rate = splits[4].replace("rate=", "").replace(";", "").toInt()
            val valves = input.split("valve")[1].replace("s", "").split(",").trimEach()
            return Valve(name, rate, valves.toSet())
        }
    }
}

data class Travel(val timeLeft: Int, val opened: Set<Valve>, val position: Valve, val partTwo: Boolean = false)
