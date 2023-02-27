package day8

import kotlinx.coroutines.*

fun main() {
    countVisibleTreesFromOutside()

}

/**
 * Count visible trees from the outside
 */
private fun countVisibleTreesFromOutside() {
    val lines = {}.javaClass.getResourceAsStream("/day8/input.txt")?.bufferedReader()?.readLines()
    val grid: List<List<Int>>? = lines?.map { line ->
        line.toCharArray().map { Character.getNumericValue(it) }
    }!!


    if (grid != null) {
        prettyPrint(grid)

        // create Nodes
        val intNodeArrays: Array<Array<IntNode>> = Array(grid.size){
            Array(grid[0].size){ IntNode() }
        }
        eval(grid, intNodeArrays)

        for (i in 0 until intNodeArrays.size) {
            for (j in 0 until intNodeArrays[i].size) {

                print("(${grid[i][j]})${intNodeArrays[i][j]} ")
            }
            println()
        }
        val max = intNodeArrays.flatten().maxBy { it.score() }.score()
        println("The answer is: $max")
    }

}

private fun prettyPrint(grid: List<List<Int>>) {
    for (elements in grid) {
        for (element in elements) {
            print(element)
        }
        println("")
    }
}

/***
 * X is left to right, y is down to up in a standard 2D plane
 *
 * When looking at input.txt, refer numbers in the file as these directions
 * South < (right to left in the file)
 * West ^ (up to down in the file)
 * East V (down to upwards in the file)
 * North > (left to right in the file)
 */

private fun eval(grid: List<List<Int>>, intNodeArrays: Array<Array<IntNode>>) {
    for (x in 0 until grid.size) {
        for (y in 0 until grid[x].size) {
            // for each number, we have to look through all the left, up, down, right
            runBlocking {
                countNorth(grid, intNodeArrays, x, y)
                countSouth(grid, intNodeArrays, x, y)
                countEast(grid, intNodeArrays, x, y)
                countWest(grid, intNodeArrays, x, y)
            }
        }
    }
}


private fun countNorth(grid: List<List<Int>>, intNodeArrays: Array<Array<IntNode>>, x: Int, y:Int) {
    var count = 0
    val currentValue = grid[x][y]
    val start = y + 1
    for (i in start until grid[x].size) {
        count++
        if (grid[x][i] >= currentValue) {
            break
        }
    }
    intNodeArrays[x][y].northCount = count
}

private fun countSouth(grid: List<List<Int>>, intNodeArrays: Array<Array<IntNode>>, x: Int, y:Int) {
    var count = 0
    val currentValue = grid[x][y]
    val start = y - 1
    if (start >= 0) {
        for (i in start downTo 0) {
            count++
            if (grid[x][i] >= currentValue) {
                break
            }
        }
        intNodeArrays[x][y].southCount = count
    }

}

private fun countEast(grid: List<List<Int>>, intNodeArrays: Array<Array<IntNode>>, x: Int, y:Int) {
    var count = 0
    val currentValue = grid[x][y]
    val start = x + 1
    for (i in start until grid.size) {
        count++
        if (grid[i][y] >= currentValue) {
            break
        }
    }
    intNodeArrays[x][y].eastCount = count

}

private fun countWest(grid: List<List<Int>>, intNodeArrays: Array<Array<IntNode>>, x: Int, y:Int) {
    var count = 0
    val currentValue = grid[x][y]
    val start = x - 1
    if (start >= 0) {
        for (i in start downTo 0) {
            count++
            if (grid[i][y] >= currentValue) {
                break
            }
        }
        intNodeArrays[x][y].westCount = count
    }
}

data class IntNode(
    var northCount: Int = 0,
    var southCount: Int = 0,
    var eastCount: Int = 0,
    var westCount: Int = 0,
) {

    fun score(): Int {
        return northCount * southCount * eastCount * westCount
    }

    override fun toString(): String {
        return "(N${northCount},S${southCount},E${eastCount},W${westCount})"
    }
}
