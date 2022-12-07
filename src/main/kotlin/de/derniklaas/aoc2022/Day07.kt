package de.derniklaas.aoc2022

fun main() {
    val input = AbstractFileType.fromInput(readFile("Day07"))
    Day07(input).execute()
}

class Day07(private val input: Folder) : Day {

    private val allFolders = mutableListOf<Folder>()
    override fun part1(): Int {
        val folders = mutableSetOf(input)
        val correctFolders = mutableSetOf<Folder>()

        while (folders.isNotEmpty()) {
            val folder = folders.first()
            folders -= folder
            allFolders += folder
            if (folder.getSize() <= 100000) {
                correctFolders += folder
            }

            for (file in folder.content) {
                if (file is Folder) {
                    folders += file
                }
            }
        }

        return correctFolders.sumOf { it.getSize() }
    }

    override fun part2(): Int {
        val freeSpace = 70000000 - input.getSize()
        val folder = allFolders.filter { it.getSize() >= 30000000 - freeSpace }
            .minByOrNull { it.getSize() }!!
        return folder.getSize()
    }
}

abstract class AbstractFileType {
    companion object {
        fun fromInput(input: List<String>): Folder {
            val rootNode = Folder("/", null, mutableSetOf())

            var currentNode = rootNode

            for (line in input) {
                if (line.startsWith("$")) { // Command
                    val command = line.replace("$ ", "")
                    when (command.split(" ")[0]) {
                        "cd" -> {
                            val folder = command.split(" ")[1]
                            currentNode = when (folder) {
                                "/" -> rootNode
                                ".." -> currentNode.parent ?: rootNode
                                else -> {
                                    currentNode.content += Folder(
                                        "${currentNode.name}$folder/",
                                        currentNode,
                                        mutableSetOf()
                                    )
                                    currentNode.getFolder(folder)
                                }
                            }
                        }

                        "ls" -> {}
                        else -> error("Unknown command $line")
                    }

                } else { // Files
                    val (a, b) = line.split(" ")
                    if (a == "dir") {
                        currentNode.content += Folder(
                            "${currentNode.name}$b/",
                            currentNode,
                            mutableSetOf()
                        )
                    } else {
                        currentNode.content += File(b, a.toInt())
                    }
                }
            }
            return rootNode
        }
    }

    abstract fun getSize(): Int
}

data class Folder(val name: String, val parent: Folder?, val content: MutableSet<AbstractFileType>) :
    AbstractFileType() {
    override fun getSize(): Int = content.sumOf { it.getSize() }

    fun getFolder(name: String): Folder {
        content.forEach {
            if (it is Folder) {
                if (it.name == "${this.name}$name/") {
                    return it
                }
            }
        }
        error("Folder not found")
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Folder) return false
        return other.name == this.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return "Folder(name=$name)"
    }
}

data class File(val name: String, private val size: Int) : AbstractFileType() {
    override fun getSize(): Int = size
}
