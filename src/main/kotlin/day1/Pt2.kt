package day1

fun main() {
    var buffer = 0
    val sortedList: MutableList<Int> = mutableListOf(1);

    {}.javaClass.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()?.forEach {
        if (it.isNotBlank())
            buffer += Integer.parseInt(it)
        else {
            sortedList.add(buffer)
            buffer = 0
        }
    }
    sortedList.add(buffer)
    sortedList.sortDescending()

    println(sortedList[0] + sortedList[1] + sortedList[2])
}
