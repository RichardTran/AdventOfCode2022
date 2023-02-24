package day3

fun main() {
    var sum = 0


    // a-z is 1-26
    // A-Z is 27-52
    // so char asci value + 1
    // each line, find common characters

    {}.javaClass.getResourceAsStream("/day3/input.txt")?.bufferedReader()?.readLines()?.forEach { it ->
        val bag1 = it.substring(0, it.length / 2).toSet()
        val bag2 = it.substring(it.length / 2, it.length).toSet()

        val sharedItems = bag1.intersect(bag2)
        sharedItems.forEach {it2 ->
            sum += if (it2.isUpperCase()) {
                it2 - 'A' + 1 + 26
            } else {
                it2 - 'a' + 1
            }
        }
    }

    println(sum)
}
