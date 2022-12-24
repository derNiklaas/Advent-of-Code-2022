package de.derniklaas.aoc2022

import kotlin.math.abs

data class Vec2D(val x: Int, val y: Int) {
    fun isNeighbour(other: Vec2D): Boolean = abs(other.x - x) <= 1 && abs(other.y - y) <= 1

    fun manhattanDistance(other: Vec2D): Int = abs(other.x - x) + abs(other.y - y)

    operator fun plus(other: Vec2D): Vec2D = Vec2D(x + other.x, y + other.y)

    operator fun minus(other: Vec2D): Vec2D = Vec2D(x - other.x, y - other.y)

    operator fun times(times: Int) = Vec2D(x * times, y * times)
}

operator fun <T> List<List<T>>.get(point: Vec2D): T = this[point.x][point.y]

fun <T> List<List<T>>.getOrNull(point: Vec2D): T? = this.getOrNull(point.x)?.getOrNull(point.y)

operator fun <T> List<MutableList<T>>.set(point: Vec2D, t: T) {
    this[point.x][point.y] = t
}
