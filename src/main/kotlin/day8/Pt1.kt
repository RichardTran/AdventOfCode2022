package day8

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

    val treeCount: Int = grid?.get(0)
        ?.size?.minus(2)?.times(2)
        ?.let { grid.size.times(2).plus(it) } ?: 0

    /**
     * Inner square corners [1,1]; [1, y - 1]; [x-1, 1]; [x-1, y-1]
     */

    /**
     * Loop through and "check" if each tree is "visible". Visible is if ALL trees (up, down, left, right)
     * are smaller than itself
     *
     * Same heights blocks (5 blocks 5)
     *
     * Inefficient brute force:
     *  - For each coordinate, we loop through y-axes (up to down), and x-axes (left to right)
     *
     * More efficient:
     *  Predetermine each point 4 directions inwards (edges toward itself) and check
     *  if there's a max equal or greater than itself
     */
    if (grid != null) {
        prettyPrint(grid)

        // create Nodes
        val boolNodeArrays: Array<Array<BoolNode>> = Array(grid.size){
            Array(grid[0].size){ BoolNode() }
        }
        eval(grid, boolNodeArrays)

        var count = 0
        for (i in 1 until boolNodeArrays.size - 1) {
            for (j in 1 until boolNodeArrays[i].size - 1) {
                if (boolNodeArrays[i][j].isVisible()) {
                    print("Y")
                    count++
                } else {
                    print("N")
                }
                print("(${grid[i][j]})${boolNodeArrays[i][j]} ")
            }
            println()
        }
        count += treeCount
        println("The answer is: $count")
    }

}

private fun prettyPrint(grid: List<List<Int>>) {
    for (trees in grid) {
        for (tree in trees) {
            print(tree)
        }
        println("")
    }
}

/***
 * Treating grid as tradition 2D in the first quad - x is left to right, y is down to up
 *
 * When looking at input.txt, refer numbers in the file as these directions
 * South < (right to left in the file)
 * West ^ (up to down in the file)
 * East V (down to upwards in the file)
 * North > (left to right in the file)
 */

private fun eval(grid: List<List<Int>>, boolNodeArrays: Array<Array<BoolNode>>) {
    evalUpToDown(grid, boolNodeArrays)
    evalDownToUp(grid, boolNodeArrays)
    evalRightToLeft(grid, boolNodeArrays)
    evalLeftToRight(grid, boolNodeArrays)
}


private fun evalLeftToRight(grid: List<List<Int>>, boolNodeArrays: Array<Array<BoolNode>>){
    for (j in 1 until grid[0].size - 1) {
        var max = grid[0][j]

        for (i in 1 until grid.size - 1) {
            if (grid[i][j] <= max) {
                boolNodeArrays[i][j].hiddenFromWest = true
            } else {
                max = grid[i][j]
            }
        }
    }
}

private fun evalRightToLeft(grid: List<List<Int>>, boolNodeArrays: Array<Array<BoolNode>>){
    for (j in 1 until grid[0].size - 1) {
        var max = grid[grid.size - 1][j]

        for (i in grid.size - 2 downTo 1) {
            if (grid[i][j] <= max) {
                boolNodeArrays[i][j].hiddenFromEast = true
            } else {
                max = grid[i][j]
            }
        }
    }
}

private fun evalDownToUp(grid: List<List<Int>>, boolNodeArrays: Array<Array<BoolNode>>){
    for (i in 1 until grid.size - 1) {
        var max = grid[i][0]

        for (j in 1 until grid[i].size - 1) {
            if (grid[i][j] <= max) {
                boolNodeArrays[i][j].hiddenFromSouth = true
            } else {
                max = grid[i][j]
            }
        }
    }
}

private fun evalUpToDown(grid: List<List<Int>>, boolNodeArrays: Array<Array<BoolNode>>){
    for (i in 1 until grid.size - 1) {
        var max = grid[i][grid[0].size - 1]

        for (j in grid[i].size - 2 downTo 1) {
            if (grid[i][j] <= max) {
                boolNodeArrays[i][j].hiddenFromNorth = true
            } else {
                max = grid[i][j]
            }
        }
    }
}


fun Boolean.toInt() = if (this) 1 else 0


data class BoolNode(
    var hiddenFromNorth: Boolean = false,
    var hiddenFromSouth: Boolean = false,
    var hiddenFromEast: Boolean = false,
    var hiddenFromWest: Boolean = false,
) {
    fun isHidden() = hiddenFromNorth && hiddenFromSouth && hiddenFromEast && hiddenFromWest
    fun isVisible() = !isHidden()

    override fun toString(): String {
        return "(N${hiddenFromNorth.toInt()},S${hiddenFromSouth.toInt()},E${hiddenFromEast.toInt()},W${hiddenFromWest.toInt()})"
    }
}
