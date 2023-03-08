package day11

import java.lang.RuntimeException

fun main() {
    val monkeys = parse("/day11/input.txt")
    for (i in 0 until 20) {
        for (monkey in monkeys) {
            monkey.inspectItemsAndPassToOtherMonkeys(monkeys)
        }
    }

    val answer = monkeys
        .sortedBy{ it.totalInspections }
        .subList(monkeys.size - 2, monkeys.size)
        .map { it.totalInspections }
        .fold(1) { acc, num -> acc * num }

    println("The answer is $answer")
}

fun parse(resource: String): List<Monkey> {
    val lines = {}.javaClass.getResourceAsStream(resource)?.bufferedReader()?.readLines()
    val linesToReadAtATime = 7

    val monkeys: MutableList<Monkey> = mutableListOf()
    if (lines != null) {
        for (i in 0 .. lines.size / linesToReadAtATime) {
            val items = parseStartingItems(lines[linesToReadAtATime * i + 1])
            val op = parseOperation(lines[linesToReadAtATime * i + 2])
            val divisibleTest = Integer.parseInt(parseTest(lines[linesToReadAtATime * i + 3]))
            val monkeyIfTrue = Integer.parseInt(parseTrue(lines[linesToReadAtATime * i + 4]))
            val monkeyIfFalse = Integer.parseInt(parseFalse(lines[linesToReadAtATime * i + 5]))

            monkeys.add(
                Monkey(
                    currentItems = items.map { Item(Integer.parseInt(it)) }.toMutableList(),
                    operation = op,
                    divisibleTest = parseConditions(divisibleTest, monkeyIfTrue, monkeyIfFalse)
                )
            )
        }
    } else {
        throw RuntimeException("Unable to parse")
    }
    return monkeys
}

fun parseStartingItems(line: String): List<String> {
    return line.substringAfter("Starting items: ").split(", ")
}

fun parseOperation(line: String): (Item) -> Int {
    val operationString = line
        .substringAfter("Operation: new = old ")
        .split(" ")

    return when {
        operationString[0] == "+" && isNumber(operationString[1]) -> {
                item: Item -> (item.worry + Integer.parseInt(operationString[1])) / 3
        }
        operationString[0] == "*" && isNumber(operationString[1]) -> {
                item: Item -> (item.worry * Integer.parseInt(operationString[1])) / 3
        }
        operationString[0] == "+" -> {
                item: Item -> (item.worry + item.worry) / 3
        }
        operationString[0] == "*" -> {
                item: Item -> (item.worry * item.worry) / 3
        }
        else -> {
            throw RuntimeException("Unexpected operation")
        }
    }
}

fun parseConditions(divisibleTest: Int, monkeyIfTrue: Int, monkeyIfFalse: Int): (Item) -> Int {
    return { item: Item->
        if (item.worry % divisibleTest == 0) {
            monkeyIfTrue
        } else {
            monkeyIfFalse
        }
    }
}

fun parseTest(line: String): String {
    return line.substringAfter("Test: divisible by ")
}

fun parseTrue(line: String): String {
    return line.substringAfter("If true: throw to monkey ")
}

fun parseFalse(line: String): String {
    return line.substringAfter("If false: throw to monkey ")
}

fun isNumber(s: String): Boolean {
    return s.all { Character.isDigit(it) }
}

/**
 * [operation]:
 *
 * Example new = old * 5 -- old being before inspection. New is after inspection
 *
 * [divisibleTest]: decide where to throw an item next
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
    val currentItems: MutableList<Item>,
    val operation: (Item) -> Int,
    val divisibleTest: (Item) -> Int,
    var totalInspections: Int = 0
) {
    fun inspectItemsAndPassToOtherMonkeys(monkeys: List<Monkey>) {
        while (currentItems.isNotEmpty()) {
            val item = currentItems.removeFirst()
            totalInspections++

            item.worry = this.operation(item)
            monkeys[divisibleTest(item)].currentItems.add(item)
        }
    }
}

data class Item(var worry: Int)
