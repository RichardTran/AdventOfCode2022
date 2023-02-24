package day6

fun main() {

    // sliding window ensuring all 4 in the window are unique chars

    val encryptedMessage = {}.javaClass.getResourceAsStream("/day6/input.txt")?.bufferedReader()?.readLines()?.get(0)

    // find 4 unique characters
    val k = 4
    var start = 0
    val hashMap = HashMap<Char, Int>()

    run outer@ {
        encryptedMessage?.forEachIndexed { index, c ->
            if (index - start == k) {
                println(index)
                return@outer // kind of equivalent to Java break
            } else if (hashMap.containsKey(c)) {
                val prevIndex = hashMap[c]
                for (i in start .. prevIndex!!)
                    hashMap.remove(encryptedMessage[i])
                start = prevIndex.plus(1)
                hashMap[c] = index
            } else {
                hashMap[c] = index
            }
        }
    }

}
