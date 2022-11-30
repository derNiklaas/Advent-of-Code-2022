package de.derniklaas.aoc2022

import java.io.File

public fun readFile(name: String, test: Boolean = false) =
    File("src/${if (test) "test" else "main"}/resources/$name.txt").readLines()

public fun List<String>.mapToInt() = map(String::toInt)

public fun List<String>.splitAndMapToInt(deliminator: String = " ") = map { it.splitAndMapToInt(deliminator) }

public fun List<String>.splitAndMapToInt(deliminator: Regex) = map { it.splitAndMapToInt(deliminator) }

public fun String.splitAndMapToInt(deliminator: String = " ") = split(deliminator).filter(String::isNotEmpty).mapToInt()

public fun String.splitAndMapToInt(deliminator: Regex) = split(deliminator).filter(String::isNotEmpty).mapToInt()

public fun String.isLowerCase() = all(Char::isLowerCase)

public fun String.isUpperCase() = all(Char::isUpperCase)

public fun StringBuilder.deletePrefix(length: Int): StringBuilder = delete(0, length)

public fun StringBuilder.takeAndDelete(length: Int): String {
    val result = take(length)
    deletePrefix(length)
    return result.toString()
}

public fun Set<Int>.allPermutations(): Set<List<Int>> {
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
