package de.derniklaas.aoc2022

interface Day {
    fun execute() {
        println("Running ${this::class.simpleName}")
        println("Part 1: ${part1()}")
        println("Part 2: ${part2()}")
    }

    fun part1(): Any
    fun part2(): Any
}