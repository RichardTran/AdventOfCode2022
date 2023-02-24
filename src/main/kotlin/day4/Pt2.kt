package day4


fun main() {

    var sum = 0
    {}.javaClass.getResourceAsStream("/day4/input.txt")?.bufferedReader()?.readLines()?.forEach { buffer ->
        val line = buffer.split(",")
        val range1 = line[0].split("-").map { Integer.parseInt(it) }
        val range2 = line[1].split("-").map { Integer.parseInt(it) }
        if (isOverlapping(range1, range2) || isOverlapping(range2, range1)) {
            sum++
        }
    }
    println(sum)

}

/**
 * An overlap happens when the min of range1 is in between range2 or the max of range1 is in between range2
 *
 * Example: (1, 3) and (2, 4). In this case, the value 3 (max of range1) is in between values 2 and 4
 */
fun isOverlapping(range1: List<Int>, range2: List<Int>): Boolean {
    return (range1[0] >= range2[0] && range1[0] <= range2[1] || range1[1] >= range2[0] && range1[1] <= range2[1])
}
