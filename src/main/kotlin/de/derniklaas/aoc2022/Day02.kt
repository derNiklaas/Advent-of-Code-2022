package de.derniklaas.aoc2022

fun main() {
    val input = Match.fromInput(readFile("Day02"))
    Day02(input).execute()
}

class Day02(private val input: List<Match>) : Day {
    override fun part1(): Int = input.sumOf { it.getScore() }

    override fun part2(): Int = input.map { it.fixMatch() }.sumOf { it.getScore() }
}

class Match(private val player1: RPSType, private val player2: RPSType) {
    companion object {
        fun fromInput(input: List<String>): List<Match> {
            return input.map {
                it.split(" ").map { type -> type.toRPSType() }
            }.map { Match(it.first(), it.last()) }
        }
    }

    fun getScore() = player2.points + getResult()

    private fun getResult(): Int {
        return if (player1.getStrongType() == player2.getNormalType()) 6
        else if (player1.getNormalType() == player2.getNormalType()) 3
        else 0
    }

    fun fixMatch(): Match {
        val first = player1
        val second = when (player2) {
            RPSType.X -> first.getWeakType()
            RPSType.Y -> first
            RPSType.Z -> first.getStrongType()
            else -> {
                error("Invalid state")
            }
        }
        return Match(first, second)
    }
}

enum class RPSType(val points: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
    X(1),
    Y(2),
    Z(3);

    fun getNormalType(): RPSType = when (this) {
        ROCK, PAPER, SCISSORS -> this
        X -> ROCK
        Y -> PAPER
        Z -> SCISSORS
    }

    fun getStrongType() = when (this) {
        ROCK, X -> PAPER
        PAPER, Y -> SCISSORS
        SCISSORS, Z -> ROCK
    }

    fun getWeakType() = when (this) {
        ROCK, X -> SCISSORS
        PAPER, Y -> ROCK
        SCISSORS, Z -> PAPER
    }
}

private fun String.toRPSType() = when (this) {
    "A" -> RPSType.ROCK
    "B" -> RPSType.PAPER
    "C" -> RPSType.SCISSORS
    "X" -> RPSType.X
    "Y" -> RPSType.Y
    "Z" -> RPSType.Z
    else -> {
        error("Invalid state")
    }
}
