package de.derniklaas.aoc2022

fun main() {
    val input = Match.fromInput(readFile("Day02"))
    Day02(input).execute()
}

class Day02(private val input: List<Match>) : Day {
    override fun part1(): Int {
        return input.sumOf {
            it.player2.points + it.getResult()
        }
    }

    override fun part2(): Int {
        return input.map { it.fixMatch() }.sumOf {
            it.player2.points + it.getResult()
        }
    }
}

class Match(private val player1: RPSType, val player2: RPSType) {
    companion object {
        fun fromInput(input: List<String>): List<Match> {
            return input.map {
                it.split(" ").map { type ->
                    when (type) {
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
                }
            }.map {
                val (first, second) = it
                Match(first, second)
            }
        }
    }

    fun getResult(): Int {
        return if (player1.getLosingType(player1) == player2.getNormalType(player2)) 6
        else if (player1.getWinningType(player1) == player2.getNormalType(player2)) 0
        else 3
    }

    fun fixMatch(): Match {
        val first = player1
        val second = when (player2) {
            RPSType.X -> {
                when (first) {
                    RPSType.ROCK -> RPSType.SCISSORS
                    RPSType.SCISSORS -> RPSType.PAPER
                    RPSType.PAPER -> RPSType.ROCK
                    else -> {
                        error("Invalid state")
                    }
                }
            }

            RPSType.Y -> {
                when (first) {
                    RPSType.ROCK -> RPSType.ROCK
                    RPSType.SCISSORS -> RPSType.SCISSORS
                    RPSType.PAPER -> RPSType.PAPER
                    else -> {
                        error("Invalid state")
                    }
                }
            }

            RPSType.Z -> {
                when (first) {
                    RPSType.ROCK -> RPSType.PAPER
                    RPSType.SCISSORS -> RPSType.ROCK
                    RPSType.PAPER -> RPSType.SCISSORS
                    else -> {
                        error("Invalid state")
                    }
                }
            }

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

    fun getNormalType(type: RPSType): RPSType = when (type) {
        ROCK -> ROCK
        PAPER -> PAPER
        SCISSORS -> SCISSORS
        X -> ROCK
        Y -> PAPER
        Z -> SCISSORS
    }

    fun getLosingType(type: RPSType) = when (type) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
        X -> PAPER
        Y -> SCISSORS
        Z -> ROCK
    }

    fun getWinningType(type: RPSType) = when (type) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
        X -> SCISSORS
        Y -> ROCK
        Z -> PAPER
    }
}
