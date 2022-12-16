package de.derniklaas.aoc2022

import kotlin.math.absoluteValue

fun main() {
    val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    val input = readFile("Day15").map {
        val (sensorX, sensorY, beaconX, beaconY) = regex.find(it)!!.destructured.toList().mapToInt()
        SensorAndBeacon(Vec2D(sensorX, sensorY), Vec2D(beaconX, beaconY))
    }
    Day15(2000000, 4000000, input).execute()
}

class Day15(private val lineToScan: Int, private val searchArea: Int, private val input: List<SensorAndBeacon>) : Day {
    override fun part1(): Int {
        val minX = input.minOf { (-it.sensor.x.absoluteValue) - it.sensor.manhattanDistance(it.beacon) }
        val maxX = input.maxOf { it.sensor.x.absoluteValue + it.sensor.manhattanDistance(it.beacon) }
        val count = (minX..maxX).count { x ->
            val point = Vec2D(x, lineToScan)
            if (input.any { it.sensor == point }) false
            else if (input.any { it.beacon == point }) false
            else {
                input.any { it.distanceToNearestBeacon >= it.sensor.manhattanDistance(point) }
            }
        }
        return count
    }

    override fun part2(): Long {
        val range = 0..searchArea
        val point = input.asSequence().flatMap {
            val distance = it.distanceToNearestBeacon + 1
            (-distance..distance).asSequence().flatMap { dx ->
                val dy = distance - dx.absoluteValue
                val sensor = it.sensor
                listOf(Vec2D(sensor.x + dx, sensor.y - dy), Vec2D(sensor.x + dx, sensor.y + dy))
            }
        }.filter { it.x in range && it.y in range }
            .first { point ->
                input.none { it.sensor.manhattanDistance(point) < it.distanceToNearestBeacon + 1 }
            }
        return point.x.toLong() * 4000000 + point.y.toLong()
    }
}

data class SensorAndBeacon(val sensor: Vec2D, val beacon: Vec2D) {
    val distanceToNearestBeacon = sensor.manhattanDistance(beacon)
}