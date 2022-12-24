package de.derniklaas.aoc2022

import java.io.File

fun readFile(name: String, test: Boolean = false) =
    File("src/${if (test) "test" else "main"}/resources/$name.txt").readLines()

fun List<String>.trimEach() = map(String::trim)

fun List<String>.mapToInt() = map(String::toInt)

fun List<String>.splitAndMapToInt(deliminator: String = " ") = map { it.splitAndMapToInt(deliminator) }

fun List<String>.splitAndMapToInt(deliminator: Regex) = map { it.splitAndMapToInt(deliminator) }

fun String.splitAndMapToInt(deliminator: String = " ") = split(deliminator).filter(String::isNotEmpty).mapToInt()

fun String.splitAndMapToInt(deliminator: Regex) = split(deliminator).filter(String::isNotEmpty).mapToInt()

fun String.isLowerCase() = all(Char::isLowerCase)

fun String.isUpperCase() = all(Char::isUpperCase)

fun StringBuilder.deletePrefix(length: Int): StringBuilder = delete(0, length)

fun StringBuilder.takeAndDelete(length: Int): String {
    val result = take(length)
    deletePrefix(length)
    return result.toString()
}

fun Set<Int>.allPermutations(): Set<List<Int>> {
    if (this.isEmpty()) return emptySet()
    return _allPermutations(this.toList())
}

// from Tenfour04 on https://stackoverflow.com/a/59737650/9473036

private fun <T> _allPermutations(list: List<T>): Set<List<T>> {
    if (list.isEmpty()) return setOf(emptyList())

    val result: MutableSet<List<T>> = mutableSetOf()
    for (i in list.indices) {
        _allPermutations(list - list[i]).forEach { item -> result.add(item + list[i]) }
    }
    return result
}

operator fun <T> List<T>.get(range: IntRange) = subList(range.first, range.last + 1)

/** Maps a given int to [0..[other]]*/
infix fun Int.within(other: Int): Int {
    var index = this
    if (this < 0) {
        index += other * (-index / other + 1)
    }
    return index % other
}
