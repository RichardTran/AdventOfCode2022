package day2

fun main() {
    // calculate total score for pt1 of day2
    // 1 for rock (X), 2 for paper (y), 3 for scissor (z)
    // calculate 0 lost, 3 draw, 6 win


    /**
     * AX - 4 - 1 for rock, 3 for draw
     * AY - 8 - 2 for paper. 6 points winning
     * AZ - 3 - 3 for scissors. 0 for losing
     * BX - 1 - 1 for paper. 0 for lost
     * BY - 5 - 2 for paper and 3 for draw
     * BZ - 9 - 3 for scissors. 6 for winning
     * CX - 7 - 1 for rock. 6 for winning
     * CY - 2 - 2 for paper. 0 for losing
     * CZ - 6 - 3 for scissors. 3 for draw
     * */
    val map: Map<String, Int> = mapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6
    )
    var totalScore = 0

    {}.javaClass.getResourceAsStream("/day2/input.txt")?.bufferedReader()?.readLines()?.forEach{
        println(it)
        totalScore += map[it]!!
    }
    println(totalScore)
}
