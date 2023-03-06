package day0

/**
 * Serves to list out things I personally learned about Kotlin while working through Advent Of Code 2023
 *
 * Will work as an interactive tutorial eventually
 */
fun main() {
    stringReplacement()
}

/**
 * Reading a file
 *
 * {} represents a generic object since we can't reference main itself to access files
 * in the resource folder
 */
fun readAFile() {
    {}.javaClass.getResourceAsStream("/day1/Pt1.txt")?.bufferedReader()?.readLines()
}

/**
 * println {s} and println(s) are different
 *
 * println { s } prints "Function0<java.lang.String>"
 *
 * println(s) prints "/Users/SomeUser/path"
 */
fun printlnExamples() {
    val s = "Test"
    println(s)
    println {s}
}

/**
 * Instead of using @param and @return like in Java, one SHOULD use square
 * brackets around param names in a method for easier reference in IntelliJ
 * like we do for [exampleParam1] and the other param [exampleParam2]
 *
 * [exampleParam3] doesn't exist so it won't be highlighted despite having square brackets
 */
fun kotlinDocsAndCommenting(exampleParam1: String, exampleParam2: Int) {}

/**
 * The obvious. Different from other coding languages using <Key> "to" <Value>
 * This works well with [key]
 */
fun creatingAMap(key: String) {
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
}

/**
 * Helpful when you are iterating through every element like forEach
 * and also want the benefit of an index number
 *
 * Output:
 * (0) apple
 * (1) pear
 * (2) banana
 */
fun forEachIndexed() {
    val list = listOf("apple", "pear", "banana")
    list.forEachIndexed { index: Int, s: String ->
        println("($index) $s")
    }
}

/**
 * How Kotlin deals with set members - what's a duplicate, what isn't?
 *
 * Output:
 *
 * [Test1(a=1, b=2)]
 *
 * [day0.Test2@300ffa5d, day0.Test2@1f17ae12]
 */
fun kotlinSetEquality() {
    data class Test1(val a: Int, val b: Int)
    class Test2(val a: Int, val b: Int) {}

    val x = Test1(1, 2)
    val y = Test1(1, 2)

    val a = Test2(1, 2)
    val b = Test2(1, 2)

    val set1 = setOf(x, y) // retains one element
    val set2 = setOf(a, b) // retains both elements

    println(set1)
    println(set2)
    set2.forEach {
        println("${it.a}--${it.b}")
    }
}

/**
 * Overriding toString() for a data class is not required
 *
 * By default, a data class's toString() outputs
 * classname(field1=value1, field2=value2)
 *
 * String Replacement and overriding toString for a data class
 *
 * Output:
 *
 * Test1(a=1, b=2)
 *
 * (N0,S1,E0,W1)
 */
fun stringReplacement() {
    // Extension to Boolean class
    fun Boolean.toSomeInt(): Int {
        return if (this) 1 else 0
    }
    data class Test1(val field1: String, val field2: String)
    data class Test2(
        var hiddenFromNorth: Boolean = false,
        var hiddenFromSouth: Boolean = false,
        var hiddenFromEast: Boolean = false,
        var hiddenFromWest: Boolean = false,
    ) {
        override fun toString(): String {
            return "(N${hiddenFromNorth.toSomeInt()},S${hiddenFromSouth.toSomeInt()},E${hiddenFromEast.toSomeInt()},W${hiddenFromWest.toSomeInt()})"
        }
    }

    val toPrint = Test1("apple", "pear")

    println(toPrint)
    println(Test2(hiddenFromNorth = false, hiddenFromSouth = true, hiddenFromEast = false, hiddenFromWest = true))
}

/**
 * Data class is a simple class which is used to hold data/state and contains standard functionality.
 * A data keyword is used to declare a class as a data class.
 * With data classes, we can considerably reduce the boilerplate code. Compiler automatically creates the equals, hashCode, toString, and copy functions.
 *
 * Differences between data classes and normal classes.
 *
 * 1. A data class must be declared with at least one primary constructor parameter which must be declared with val or var.
 * A normal class can be defined with or without a parameter in its constructor.
 *
 * 2. Data classes have default implementations for the following methods using only properties that were declared in the primary constructor;
 * toString(), hashCode(), copy(), componentN(), equals().
 * Implementation for those methods can be written in normal classes using properties that were and were not declared in
 * the primary constructor.
 *
 * 3. A data class cannot be extended by another class. They are final classes by default. Normal classes can be extended by other classes, including data classes. Certain conditions should however be met.
 *
 * 4. Data classes cannot be sealed, open, abstract or inner. Normal classes can be any of these.
 *
 * Source: https://medium.com/@dubemezeagwu/difference-between-normal-classes-data-classes-in-kotlin-a01f636e8900
 */
class UserClass {
    val name: String = "Richard"
    val email: String = "Email"
    fun info() = "This is $name and my email is $email - vanilla class"
}

data class UserDataClass(val name: String = "Richard", val email: String = "Email") {
    fun info() = "This is $name and my email is $email - data class"
}
