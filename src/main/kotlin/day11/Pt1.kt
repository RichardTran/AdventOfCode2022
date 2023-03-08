package day11

fun main() {

    var mutab
    {}.javaClass.getResourceAsStream("/day11/input.txt")?.bufferedReader()?.readLines()

}


/**
 * [operation]:
 *
 * Example new = old * 5 -- old being before inspection. New is after inspection
 *
 * [test]: decide where to throw an item next
 *
 * After each monkey inspects an item but before it tests your worry level,
 * your relief that the monkey's inspection didn't damage the item causes your worry level
 * to be divided by three and rounded down to the nearest integer.
 *
 * The monkeys take turns inspecting and throwing items.
 *
 * On a single monkey's turn, it inspects and throws all of the items it is holding one at a time and in the order listed.
 * Monkey 0 goes first, then monkey 1, and so on until each monkey has had one turn.
 * The process of each monkey taking a single turn is called a round.
 *
 * Items are tossed around into a queue. No items in its turns moves on
 *
 * Count the total number of times each monkey inspects items over 20 rounds:
 *
 * In this example, the two most active monkeys inspected items 101 and 105 times.
 * The level of monkey business in this situation can be found by multiplying these together: 10605.
 *
 * Figure out which monkeys to chase by counting how many items they inspect over 20 rounds.
 *
 * What is the level of monkey business after 20 rounds of stuff-slinging simian shenanigans?
 */

data class Monkey(
    val startingItems: List<Item>,
    val operation: (Int) -> Int,
    val test: (Int) -> (Boolean) -> Int // Divide worry by 3(floor) then test | worry level -> boolean -> which monkey
)

data class Item(val worry: Int)
