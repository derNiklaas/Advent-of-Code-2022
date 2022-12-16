package de.derniklaas.aoc2022

import java.util.PriorityQueue

fun main() {
    val input = readFile("Day16").map(Valve::fromInput)
    Day16(input).execute()
}

class Day16(val input: List<Valve>) : Day {

    override fun part1(): Any {
        val distances = input.associateWith { start ->
            input.filter { it.flowRate > 0 }.associateWith { end ->
                shortedDistanceBetweenNodes(start, end)
            }
        }
        val queue = mutableListOf(Travel(30, listOf(), 0, input.first { it.name == "AA" }))

        var maxSteam = 0
        while (queue.isNotEmpty()) {
            val travel = queue.removeFirst()
            maxSteam = maxOf(travel.releasedSteam, maxSteam)
            if (travel.timeLeft <= 0) continue
            val currentPosition = travel.position
            distances[currentPosition]!!.mapNotNullTo(queue) { (candidate, distance) ->
                val timeLeft = travel.timeLeft - distance - 1
                if (timeLeft > 0 && candidate !in travel.opened) {
                    Travel(
                        timeLeft,
                        travel.opened + candidate,
                        travel.releasedSteam + candidate.flowRate * timeLeft,
                        candidate
                    )
                } else {
                    null
                }
            }
        }
        return maxSteam
    }

    override fun part2(): Any {
        return "TODO"
    }

    private fun shortedDistanceBetweenNodes(from: Valve, to: Valve): Int {
        val nodes = input.map(::Node).associateBy { it.valve.name }
        val startNode = nodes[from.name]!!.also { it.distance = 0 }
        val endNode = nodes[to.name]!!

        val queue = PriorityQueue<Node>(compareBy { it.distance })
        queue += startNode
        while (true) {
            val node = queue.remove()!!
            if (node == endNode) return endNode.distance
            node.valve.leadsTo.mapNotNullTo(queue) { name ->
                val child = nodes.getValue(name)
                if (child.distance == Int.MAX_VALUE) {
                    child.distance = node.distance + 1
                    child
                } else {
                    null
                }
            }
        }
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

data class Travel(val timeLeft: Int, val opened: List<Valve>, val releasedSteam: Int, val position: Valve)

data class Node(val valve: Valve, var distance: Int = Int.MAX_VALUE)
