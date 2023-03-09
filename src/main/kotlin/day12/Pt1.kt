package day12

import java.util.LinkedList

fun main() {
    var startX = -1
    var startY = -1
    var endX = -1
    var endY = -1
    val grid: List<List<Char>> = {}.javaClass.getResourceAsStream("/day12/input.txt")?.bufferedReader()?.readLines()
        ?.mapIndexed { index, line ->
            if (line.contains("S")) {
                startX = index
                startY = line.indexOf("S")
            }
            if (line.contains("E")) {
                endX = index
                endY = line.indexOf("E")
            }
            line.replace("E", "z").replace("S", "a").toCharArray().toList()
        }.orEmpty()

    println("The answer is: ${bfs(grid, startX, startY, endX, endY)}")
}

fun bfs(grid: List<List<Char>>, startX: Int, startY: Int, endX: Int, endY: Int): Int {
    val queue: LinkedList<Vertex> = LinkedList()
    val gridRowBounds = grid.indices
    val gridColBounds = 0 until grid[0].size
    val visited: Array<Array<Boolean>> =
        Array(grid.size) { Array(grid[0].size) { false } }

    queue.add(Vertex(startX, startY, 0))
    visited[startX][startY] = true

    while (queue.isNotEmpty()) {
        val currentVertex: Vertex = queue.removeFirst()
        println(currentVertex)
        visited[currentVertex.x][currentVertex.y] = true

        if (currentVertex.x == endX && currentVertex.y == endY) {
            return currentVertex.distance
        } else {
            if (currentVertex.x - 1 in gridRowBounds &&
                grid[currentVertex.x - 1][currentVertex.y] - grid[currentVertex.x][currentVertex.y] <= 1 &&
                !visited[currentVertex.x - 1][currentVertex.y]
            ) {
                queue.add(
                    Vertex(
                        x = currentVertex.x - 1,
                        y= currentVertex.y,
                        distance = currentVertex.distance + 1
                    )
                )
                visited[currentVertex.x - 1][currentVertex.y] = true

            }
            if (currentVertex.x + 1 in gridRowBounds &&
                grid[currentVertex.x + 1][currentVertex.y] - grid[currentVertex.x][currentVertex.y] <= 1 &&
                !visited[currentVertex.x + 1][currentVertex.y]
            ) {
                queue.add(
                    Vertex(
                        x = currentVertex.x + 1,
                        y = currentVertex.y,
                        distance = currentVertex.distance + 1
                    )
                )
                visited[currentVertex.x + 1][currentVertex.y] = true
            }
            if (currentVertex.y - 1 in gridColBounds &&
                grid[currentVertex.x][currentVertex.y - 1] - grid[currentVertex.x][currentVertex.y] <= 1 &&
                !visited[currentVertex.x][currentVertex.y - 1]
            ) {
                queue.add(
                    Vertex(
                        x = currentVertex.x,
                        y = currentVertex.y - 1,
                        distance = currentVertex.distance + 1
                    )
                )
                visited[currentVertex.x][currentVertex.y - 1] = true
            }
            if (currentVertex.y + 1 in gridColBounds &&
                grid[currentVertex.x][currentVertex.y + 1] - grid[currentVertex.x][currentVertex.y] <= 1 &&
                !visited[currentVertex.x][currentVertex.y + 1]
            ) {
                queue.add(
                    Vertex(
                        x = currentVertex.x,
                        y = currentVertex.y + 1,
                        distance = currentVertex.distance + 1
                    )
                )
                visited[currentVertex.x][currentVertex.y + 1] = true
            }
        }
    }
    return -1
}

data class Vertex(
    val x: Int,
    val y: Int,
    val distance: Int
)
