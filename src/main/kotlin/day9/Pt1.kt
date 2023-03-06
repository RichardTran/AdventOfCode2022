package day9

import java.lang.RuntimeException
import kotlin.math.abs

/**
 * https://adventofcode.com/2022/day/9
 *
 * Not proud of the work here - here rope knots and coords could be separated to not have to copy the data object
 * to store coords.
 */
fun main() {
    val lines = {}.javaClass.getResourceAsStream("/day9/input.txt")?.bufferedReader()?.readLines()
    val vectors = lines
        ?.map { it.split(" ") }
        ?.map { inputToVector(it[0], it[1]) }

    vectors?.let { solve(1, it) }
}

/**
 * Observations:
 * - If H moves into a square around T or on T, then T doesn't move.
 * - If H moves into a square not around T, then T moves into H's previous location
 */
fun solve(numOfTails: Int, vectors: List<Vector>) {
    val tailKnots = (0 until numOfTails).map {
        Coordinate(0, 0)
    }
    val rope = KnotRope(head = Coordinate(0,0), tailKnots = tailKnots)

    val tailVisited: MutableSet<Coordinate> = mutableSetOf(Coordinate(0,0))
    vectors.forEach {vector ->
        println(vector)
        tailVisited.addAll(rope.moveAndLogEndTailMovements(vector).toSet())
        println(tailVisited)
    }
    println("The answer is: ${tailVisited.size}")
}

fun inputToVector(direction: String, scalarString: String): Vector {
    val scalar = Integer.parseInt(scalarString)
    return when(direction) {
        "U" -> Vector(0, 1, scalar) // Up
        "D" -> Vector(0, -1, scalar) // Down
        "L" -> Vector(-1, 0, scalar) // Left
        "R" -> Vector(1, 0, scalar) // Right
        else -> {
            throw RuntimeException("Unexpected direction -- $direction")
        }
    }
}

data class Coordinate(var x: Int, var y: Int) {
    fun move(x: Int, y: Int) {
        this.x += x
        this.y += y
    }

    fun set(newCoords: Coordinate) {
        this.x = newCoords.x
        this.y = newCoords.y
    }

    fun isNeighbor(otherCoordinate: Coordinate): Boolean {
        return abs(this.x - otherCoordinate.x) <= 1 &&
            abs(this.y - otherCoordinate.y) <= 1
    }

    fun isNotNeighbor(otherCoordinate: Coordinate): Boolean {
        return !isNeighbor(otherCoordinate)
    }
}

data class Vector(val x: Int, val y: Int, val scalar: Int)

data class KnotRope(var head: Coordinate, var tailKnots: List<Coordinate>) {

    /**
     * If the head and tail aren't neighbors and aren't in the same row or column,
     * the tail always moves one step diagonally to keep up:
     */
    fun moveAndLogEndTailMovements(vector: Vector): List<Coordinate> {
        val tailVisitedCoords: MutableList<Coordinate> = mutableListOf(tailKnots[tailKnots.size - 1].copy())
        for (i in 0 until vector.scalar) {
            var path = moveHead(vector)
            for (knot in tailKnots) {
                path = moveKnot(path, knot)
            }

            tailVisitedCoords.add(tailKnots[tailKnots.size - 1].copy())

            /* Debugging
                println("knotlocations -- $vector")
                println("head -- $head")
                for (j in tailKnots.indices) {
                    println("${j + 1} -- ${tailKnots[j]}")
                }
                prettyPrint(head, tailKnots)
                println()
            */
        }
        return tailVisitedCoords
    }

    private fun moveHead(vector: Vector): MutableList<Coordinate> {
        val headPath: MutableList<Coordinate> = mutableListOf(head.copy())
        head.move(vector.x, vector.y)
        headPath.add(head.copy())
        return headPath
    }

    /**
     * We determine if tail moves into head's OLD location if the head's NEW location is not a neighbor
     *
     * [parentKnotPath] index 0 is the starting location and index 1 is the destination
     */
    private fun moveKnot(parentKnotPath: MutableList<Coordinate>, currentKnot: Coordinate): MutableList<Coordinate> {
        val tailPath: MutableList<Coordinate> = mutableListOf(currentKnot.copy())
        if (parentKnotPath.size > 1) { // if size is 1, it means the previous knot did not move and its path is where it already was
            if (currentKnot.isNotNeighbor(parentKnotPath[1])) {
                val parentsDestinationCoord = parentKnotPath[1]

                // Vectors in this problem always move in 8 directions (up, down, left, right or diagonal)
                val destVectorX = if (parentsDestinationCoord.x != currentKnot.x) {
                    (parentsDestinationCoord.x - currentKnot.x) / abs(parentsDestinationCoord.x - currentKnot.x)
                } else { 0 }

                val destVectorY = if (parentsDestinationCoord.y != currentKnot.y) {
                    (parentsDestinationCoord.y - currentKnot.y) / abs(parentsDestinationCoord.y - currentKnot.y)
                } else { 0 }

                val destinationVector = Vector(x = destVectorX, y = destVectorY, scalar = 1)

                currentKnot.move(destinationVector.x, destinationVector.y)
                tailPath.add(currentKnot.copy())
            }
        }
        return tailPath
    }

    /**
     * For debugging purposes
     */
    private fun prettyPrint(head: Coordinate, tailCoords: List<Coordinate>) {
        val scale = 32
        val center = scale / 2
        val grid = Array(scale){
            Array(scale){ "." }
        }
        grid[head.x + center][head.y + center] = "H"
        tailCoords.forEachIndexed { index, coordinate ->
            grid[coordinate.x + center][coordinate.y + center] = (index + 1).toString()
        }
        grid[center][center] = "S"
        for(i in grid.size - 1 downTo 0) {
            for (j in 0 until grid[i].size) {
                print(grid[j][i])
            }
            println()
        }
    }
}
/**
 * --- Day 9: Rope Bridge ---

This rope bridge creaks as you walk along it. You aren't sure how old it is, or whether it can even support your weight.

It seems to support the Elves just fine, though. The bridge spans a gorge which was carved out by the massive river far below you.

You step carefully; as you do, the ropes stretch and twist. You decide to distract yourself by modeling rope physics; maybe you can even figure out where not to step.

Consider a rope with a knot at each end; these knots mark the head and the tail of the rope. If the head moves far enough away from the tail, the tail is pulled toward the head.

Due to nebulous reasoning involving Planck lengths, you should be able to model the positions of the knots on a two-dimensional grid. Then, by following a hypothetical series of motions (your puzzle input) for the head, you can determine how the tail will move.

Due to the aforementioned Planck lengths, the rope must be quite short; in fact, the head (H) and tail (T) must always be touching (diagonally adjacent and even overlapping both count as touching):

....
.TH.
....

....
.H..
..T.
....

...
.H. (H covers T)
...
If the head is ever two steps directly up, down, left, or right from the tail, the tail must also move one step in that direction so it remains close enough:

.....    .....    .....
.TH.. -> .T.H. -> ..TH.
.....    .....    .....

...    ...    ...
.T.    .T.    ...
.H. -> ... -> .T.
...    .H.    .H.
...    ...    ...
Otherwise, if the head and tail aren't touching and aren't in the same row or column, the tail always moves one step diagonally to keep up:

.....    .....    .....
.....    ..H..    ..H..
..H.. -> ..... -> ..T..
.T...    .T...    .....
.....    .....    .....

.....    .....    .....
.....    .....    .....
..H.. -> ...H. -> ..TH.
.T...    .T...    .....
.....    .....    .....
You just need to work out where the tail goes as the head follows a series of motions. Assume the head and the tail both start at the same position, overlapping.

For example:

R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
This series of motions moves the head right four steps, then up four steps, then left three steps, then down one step, and so on. After each step, you'll need to update the position of the tail if the step means the head is no longer adjacent to the tail. Visually, these motions occur as follows (s marks the starting position as a reference point):

== Initial State ==

......
......
......
......
H.....  (H covers T, s)

== R 4 ==

......
......
......
......
TH....  (T covers s)

......
......
......
......
sTH...

......
......
......
......
s.TH..

......
......
......
......
s..TH.

== U 4 ==

......
......
......
....H.
s..T..

......
......
....H.
....T.
s.....

......
....H.
....T.
......
s.....

....H.
....T.
......
......
s.....

== L 3 ==

...H..
....T.
......
......
s.....

..HT..
......
......
......
s.....

.HT...
......
......
......
s.....

== D 1 ==

..T...
.H....
......
......
s.....

== R 4 ==

..T...
..H...
......
......
s.....

..T...
...H..
......
......
s.....

......
...TH.
......
......
s.....

......
....TH
......
......
s.....

== D 1 ==

......
....T.
.....H
......
s.....

== L 5 ==

......
....T.
....H.
......
s.....

......
....T.
...H..
......
s.....

......
......
..HT..
......
s.....

......
......
.HT...
......
s.....

......
......
HT....
......
s.....

== R 2 ==

......
......
.H....  (H covers T)
......
s.....

......
......
.TH...
......
s.....
After simulating the rope, you can count up all of the positions the tail visited at least once. In this diagram, s again marks the starting position (which the tail also visited) and # marks other positions the tail visited:

..##..
...##.
.####.
....#.
s###..
So, there are 13 positions the tail visited at least once.

Simulate your complete hypothetical series of motions. How many positions does the tail of the rope visit at least once?
 */
