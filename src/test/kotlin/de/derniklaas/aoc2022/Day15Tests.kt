package de.derniklaas.aoc2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day15Tests {
    private val regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    private val input = readFile("Day15", true).map {
        val (sensorX, sensorY, beaconX, beaconY) = regex.find(it)!!.destructured.toList().mapToInt()
        SensorAndBeacon(Vec2D(sensorX, sensorY), Vec2D(beaconX, beaconY))
    }
    private val day15 = Day15(10, 20, input)

    @Test
    fun part1() {
        Assertions.assertEquals(26, day15.part1())
    }

    @Test
    fun part2() {
        Assertions.assertEquals(56000011, day15.part2())
    }
}