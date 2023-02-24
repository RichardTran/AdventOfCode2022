package day3

fun main() {
    var sum = 0


    // a-z is 1-26
    // A-Z is 27-52
    // so char asci value + 1
    // each line, find common characters

    var setOfItems: Set<Char> = setOf();

    {}.javaClass.getResourceAsStream("/day3/input.txt")?.bufferedReader()?.readLines()?.forEachIndexed { index, s ->
        if (index % 3 == 0) {
            setOfItems = s.toSet()
        } else if (index % 3 == 1) {
            setOfItems = setOfItems.intersect(s.toSet())
        } else {
            setOfItems = setOfItems.intersect(s.toSet())
            setOfItems.forEach { it2 ->
                sum += if (it2.isUpperCase()) {
                    it2 - 'A' + 1 + 26
                } else {
                    it2 - 'a' + 1
                }
            }
        }
    }
    println(sum)
}
