package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day09").map(MovementInstruction::fromInput)
    Day09(input).execute()
}

class Day09(val input: List<MovementInstruction>) : Day {

    override fun part1(): Int = move(2)

    override fun part2(): Int = move(10)

    private fun move(length: Int): Int {
        val rope = MutableList(length) { Vec2D(0, 0) }
        val visited = mutableSetOf(rope[0])

        for (instruction in input) {
            for (i in 1..instruction.amount) {
                rope[0] += instruction.direction
                for (j in 0 until rope.size - 1) {
                    val currentTail = rope[j + 1]
                    val currentHead = rope[j]
                    if (!currentHead.isNeighbour(currentTail)) {
                        val offset = currentHead - currentTail
                        val normalizeOffset = Vec2D(offset.x.coerceIn(-1, 1), offset.y.coerceIn(-1, 1))
                        rope[j + 1] = currentTail + normalizeOffset
                    }
                }
                visited += rope.last()
            }
        }
        return visited.size
    }
}

data class MovementInstruction(val direction: Vec2D, val amount: Int) {
    companion object {
        fun fromInput(input: String): MovementInstruction {
            val (direction, amount) = input.split(" ")
            return when (direction) {
                "U" -> MovementInstruction(Vec2D(0, 1), amount.toInt())
                "R" -> MovementInstruction(Vec2D(1, 0), amount.toInt())
                "D" -> MovementInstruction(Vec2D(0, -1), amount.toInt())
                "L" -> MovementInstruction(Vec2D(-1, 0), amount.toInt())
                else -> error("Illegal Input")
            }
        }
    }
}
