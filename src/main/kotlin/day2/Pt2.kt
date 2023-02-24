package day2

fun main() {
    // calculate total score for pt1 of day2
    // 1 for rock (X), 2 for paper (y), 3 for scissor (z)
    // calculate 0 lost, 3 draw, 6 win


    /**
     * AX - 3 - 3 for scissors, 0 for lose
     * AY - 4 - 1 for rock. 3 points draw
     * AZ - 8 - 2 for paper. 6 for winning
     * BX - 1 - 1 for rock. 0 for lost
     * BY - 5 - 2 for paper and 3 for draw
     * BZ - 9 - 3 for scissors. 6 for winning
     * CX - 2 - 2 for paper. 0 for lost
     * CY - 6 - 3 for scissors. 3 for draw
     * CZ - 7 - 1 for rock. 6 for winning
     * */
    val map: Map<String, Int> = mapOf(
        "A X" to 3,
        "A Y" to 4,
        "A Z" to 8,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 2,
        "C Y" to 6,
        "C Z" to 7
    );
    var totalScore = 0

    {}.javaClass.getResourceAsStream("/day2/input.txt")?.bufferedReader()?.readLines()?.forEach{
        println(it)
        totalScore += map[it]!!
    }
    println(totalScore)
}
