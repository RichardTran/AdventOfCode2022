package day12

import java.util.LinkedList

fun main() {

    val grid: List<List<Char>> = {}.javaClass.getResourceAsStream("/day12/input.txt")?.bufferedReader()?.readLines()
        ?.map {
            it.toCharArray().toList()
        }.orEmpty()

    bfs(grid)
}

fun bfs(grid: List<List<Char>>) {
    val queue: LinkedList<Vertex> = LinkedList()
    val startingElevation = 'a'
    var startX = -1
    var startY = -1

    // Find S
    grid.forEachIndexed { x, line ->
        line.forEachIndexed { y, ch ->
            if (ch == 'S') {
                println(ch)
                startX = x
                startY = y
            }
        }
    }
    println(startX)
    println(startY)
}

data class Vertex(
    val x: Int,
    val y: Int,
    val distance: Int,
    val character: Char // elevation
)
