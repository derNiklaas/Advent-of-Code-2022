package de.derniklaas.aoc2022

import java.util.PriorityQueue

fun main() {
    val input = readFile("Day19").map(Blueprint::fromInput)
    Day19(input).execute()
}

class Day19(val input: List<Blueprint>) : Day {
    override fun part1(): Int =
        input.map { maximizeGeodes(it, 24) }.withIndex().sumOf { (index, geodes) -> geodes * (index + 1) }

    override fun part2(): Int = input.slice(0..2)
        .map { maximizeGeodes(it, 32) }.reduce { acc, i -> acc * i }

    private fun maximizeGeodes(blueprint: Blueprint, timeLeft: Int): Int {
        val queue = mutableListOf(State(timeLeft))
        val bestRobots = PriorityQueue<State>()
        val visited = mutableSetOf<State>()

        fun addState(state: State) {
            if (state in visited) return
            visited += state

            // Save 500 states for easier filtering
            for (robot in bestRobots) {
                if (robot.isBetterThan(state)) return
            }
            bestRobots += state
            if (bestRobots.size > 500) bestRobots.poll()

            queue += state
        }

        var best = 0
        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            val tickedState = state.copy().tick()
            if (tickedState.timeLeft == 0) {
                best = maxOf(best, tickedState.geode)
                continue
            }

            // Try to "craft" a geode robot
            if (state.ore >= blueprint.oreForGeodeRobot && state.obsidian >= blueprint.obsidianForGeodeRobot) {
                val nextState = tickedState.copy(
                    ore = tickedState.ore - blueprint.oreForGeodeRobot,
                    obsidian = tickedState.obsidian - blueprint.obsidianForGeodeRobot,
                    geodeRobots = tickedState.geodeRobots + 1
                )
                addState(nextState)
                // Try to "craft" an obsidian robot
            } else if (state.ore >= blueprint.oreForObsidianRobot && state.clay >= blueprint.clayForObsidianRobot) {
                val nextState = tickedState.copy(
                    ore = tickedState.ore - blueprint.oreForObsidianRobot,
                    clay = tickedState.clay - blueprint.clayForObsidianRobot,
                    obsidianRobots = tickedState.obsidianRobots + 1
                )
                addState(nextState)
            } else {
                if (state.ore >= blueprint.oreForClayRobot) {
                    val nextState = tickedState.copy(
                        ore = tickedState.ore - blueprint.oreForClayRobot, clayRobots = tickedState.clayRobots + 1
                    )
                    addState(nextState)
                }
                if (state.ore >= blueprint.oreForOreRobot) {
                    val nextState = tickedState.copy(
                        ore = tickedState.ore - blueprint.oreForOreRobot, oreRobots = tickedState.oreRobots + 1
                    )
                    addState(nextState)
                }
                addState(tickedState.copy())
            }
        }
        return best
    }
}

data class Blueprint(
    val oreForOreRobot: Int,
    val oreForClayRobot: Int,
    val oreForObsidianRobot: Int,
    val clayForObsidianRobot: Int,
    val oreForGeodeRobot: Int,
    val obsidianForGeodeRobot: Int
) {
    companion object {
        fun fromInput(input: String): Blueprint {
            val matches = Regex("\\d+").findAll(input).map { it.value.toInt() }.toList()
            return Blueprint(
                matches[1], matches[2], matches[3], matches[4], matches[5], matches[6]
            )
        }
    }
}

data class State(
    var timeLeft: Int,
    var ore: Int = 0,
    var clay: Int = 0,
    var obsidian: Int = 0,
    var geode: Int = 0,
    var oreRobots: Int = 1,
    var clayRobots: Int = 0,
    var obsidianRobots: Int = 0,
    var geodeRobots: Int = 0
) : Comparable<State> {
    private fun score() = oreRobots + clayRobots + obsidianRobots + geodeRobots

    override fun compareTo(other: State): Int = compareValuesBy(this, other) { it.score() }

    fun tick(): State {
        ore += oreRobots
        clay += clayRobots
        obsidian += obsidianRobots
        geode += geodeRobots
        timeLeft--
        return this
    }

    fun isBetterThan(other: State): Boolean = ore >= other.ore && clay >= other.clay && obsidian >= other.obsidian
            && geode >= other.geode && oreRobots >= other.oreRobots && clayRobots >= other.clayRobots
            && obsidianRobots >= other.obsidianRobots && geodeRobots >= other.geodeRobots
}

