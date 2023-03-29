package day13

fun main() {

    // Keep a counter of pairs that are in the right order
    val lines = {}.javaClass.getResourceAsStream("/day13/input.txt")
        ?.bufferedReader()
        ?.readLines()!!

    val linesToRead = 3
    var answer = 1

    for (i in 0 .. lines.size / linesToRead) {
        val leftString = lines[i / linesToRead + 0]
        val rightString = lines[i / linesToRead + 1]

        var leftPointer = Pointer(leftString)
        var rightPointer = Pointer(rightString)

        while(true) {


        }

        // left smaller is right order
        // left runs out first means right order
    }
}

data class Pointer(
    val string: String,
    var index: Int = -1,
    var level: Int = 0,
    var indexedList: Int = 0
) {

    fun currentChar(): Char {
        return string[index]
    }

    fun nextChar(): Char? {
        val temp = index + 1
        if (temp < string.length) {
            index = temp
            return string[index]
        } else {
            return null
        }
    }

    fun findNextNumber(): Int {
        var tempIndex = index
        while(tempIndex < string.length && !string[tempIndex].isDigit()) {
            if (string[tempIndex] == '[') {
                level++
            } else if (string[tempIndex] == ']') {
                level--
                indexedList++
                return -1
            }
            tempIndex++
        }
        index = tempIndex
        if (index >= string.length) {
            return -1
        } else {
            return Integer.parseInt(string[index].toString())
        }
    }
}
