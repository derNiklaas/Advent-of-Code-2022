package de.derniklaas.aoc2022

fun main() {
    val input = readFile("Day13").joinToString("\n").split("\n\n").map {
        it.split("\n").map(Packet::fromInput)
    }
    Day13(input).execute()
}

class Day13(val input: List<List<Packet>>) : Day {
    override fun part1(): Any = input.withIndex().sumOf { (index, pair) ->
        val (a, b) = pair
        if (a.compareTo(b) < 0) {
            index + 1
        } else {
            0
        }
    }

    override fun part2(): Int {
        val divider1 = Packet.fromInput("[[2]]")
        val divider2 = Packet.fromInput("[[6]]")
        val packets = sortedSetOf<Packet>({ p1, p2 -> p1.compareTo(p2) })
        packets += input.flatten() + divider1 + divider2
        return (packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1)

    }

}

data class Packet(val parent: Packet?, var value: Int?, val subPackets: MutableList<Packet>) {
    companion object {
        fun fromInput(input: String): Packet {
            val root = Packet(null, null, mutableListOf())
            input.fold(Pair(root, "")) { (currentRoot, value), c ->
                if (c.isDigit()) {
                    Pair(currentRoot, value + c)
                } else if (c == '[') {
                    val newPacket = Packet(currentRoot, null, mutableListOf())
                    currentRoot.subPackets += newPacket
                    Pair(newPacket, "")
                } else if (c == ']') {
                    if (value.isNotEmpty()) {
                        currentRoot.subPackets += Packet(currentRoot, value.toInt(), mutableListOf())
                    }
                    if (currentRoot.parent != null) { // Go up a packet
                        Pair(currentRoot.parent, "")
                    } else {
                        Pair(currentRoot, "")
                    }
                } else {
                    if (value.isNotEmpty()) {
                        currentRoot.subPackets += Packet(currentRoot, value.toInt(), mutableListOf())
                    }
                    Pair(currentRoot, "")
                }
            }
            return root
        }
    }

    fun compareTo(other: Packet): Int {
        return if (value != null && other.value != null) {
            value!! - other.value!!
        } else if (value == null && other.value == null) {
            compareList(subPackets, other.subPackets)
        } else {
            if (value != null) {
                compareList(listOf(Packet(this, value!!, mutableListOf())), other.subPackets)
            } else {
                compareList(subPackets, listOf(Packet(other, other.value!!, mutableListOf())))
            }
        }
    }

    private fun compareList(first: List<Packet>, second: List<Packet>) = first
        .zip(second)
        .map { (p1, p2) -> p1.compareTo(p2) }
        .find { it != 0 } ?: (first.size - second.size)

    override fun toString(): String {
        return "Packet[value=$value,subPackets=$subPackets"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Packet) return false
        return compareTo(other) == 0
    }

    override fun hashCode(): Int {
        var result = parent?.hashCode() ?: 0
        result = 31 * result + (value ?: 0)
        result = 31 * result + subPackets.hashCode()
        return result
    }
}
