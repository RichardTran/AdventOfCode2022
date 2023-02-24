package day4

fun main() {

    var sum = 0
    {}.javaClass.getResourceAsStream("/day4/input.txt")?.bufferedReader()?.readLines()?.forEach { buffer ->
        val line = buffer.split(",")
        val range1 = line[0].split("-").map { Integer.parseInt(it) }
        val range2 = line[1].split("-").map { Integer.parseInt(it) }
        if (isCompletelyOverlapped(range1, range2) || isCompletelyOverlapped(range2, range1)) {
            sum++
        }
    }
    println(sum)

}

fun isCompletelyOverlapped(range1: List<Int>, range2: List<Int>): Boolean {
//    println("$range1 .. $range2 .. $bool -- $b1 or $b2 -- ${range1[0]} vs ${range2[0]}")

    return (range1[0] >= range2[0] && range1[1] <= range2[1])
}
