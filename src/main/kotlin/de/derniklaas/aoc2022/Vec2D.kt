package de.derniklaas.aoc2022

import kotlin.math.abs

data class Vec2D(val x: Int, val y: Int) {
    fun isNeighbour(other: Vec2D): Boolean {
        return abs(other.x - x) <= 1 && abs(other.y - y) <= 1
    }

    fun manhattanDistance(other: Vec2D): Int = abs(other.x - x) + abs(other.y - y)

    operator fun plus(other: Vec2D): Vec2D {
        return Vec2D(x + other.x, y + other.y)
    }

    operator fun minus(other: Vec2D): Vec2D {
        return Vec2D(x - other.x, y - other.y)
    }
}
