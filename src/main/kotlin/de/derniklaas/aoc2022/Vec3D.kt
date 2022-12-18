package de.derniklaas.aoc2022

data class Vec3D(val x: Int, val y: Int, val z: Int) {

    fun getNeighbors(): List<Vec3D> = listOf(
        Vec3D(x + 1, y, z),
        Vec3D(x - 1, y, z),
        Vec3D(x, y + 1, z),
        Vec3D(x, y - 1, z),
        Vec3D(x, y, z + 1),
        Vec3D(x, y, z - 1)
    )

    operator fun plus(other: Vec3D): Vec3D {
        return Vec3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Vec3D): Vec3D {
        return Vec3D(x - other.x, y - other.y, z - other.z)
    }
}
