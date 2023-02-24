package day1

fun main() {


    //val v = this

    //{}.javaClass.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()?.forEach { println(it) };
    //{}::class.java.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()?.forEach { println(it) };
    //File("resources/day1/Pt1.txt").bufferedReader().readLines().forEach { println(it) }
    //val s: String = Paths.get("").toAbsolutePath().toString()

    /**
     * println {s} and println(s) are different
     * println { s } prints "Function0<java.lang.String>"
     * println(s) prints "/Users/SomeUser/path"
     *
     *
     */
    //println{s}

    var max = 0;
    var buffer = 0;
    {}.javaClass.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()?.forEach {
        if (it.isNotBlank())
            buffer += Integer.parseInt(it)
        else {
            if (max < buffer) {
                max = buffer
            }
            buffer = 0
        }
    }
    if (max < buffer)
        max = buffer
    println(max)
}



class B {
    fun x() {
        this.javaClass.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()?.forEach { println(it) }
    }
}
