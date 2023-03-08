package day11

import java.lang.RuntimeException

fun main() {
    val strategies = listOf(1, 100, 1_000, 10_000, 100_000, 100_000_000)

    for (strategy in strategies) {
        val monkeys = parseExtreme("/day11/input.txt")
        for (i in 0 until 20) {
            for (monkey in monkeys) {
                monkey.inspectItemsAndPassToOtherMonkeys(monkeys, strategy)
            }
        }
        val answer = monkeys
            .sortedBy{ it.totalInspections }
            .subList(monkeys.size - 2, monkeys.size)
            .map { it.totalInspections }
            .fold(1L) { acc, num -> acc * num }

        println("strategy -- $strategy")
        monkeys
            .forEachIndexed { index, monkey ->
                println("$index -- ${monkey.totalInspections} -- ${monkey.currentItems}")
            }
        println()
    }

}

fun parseExampleAnswers(resource: String) {
    val lines = {}.javaClass.getResourceAsStream("/day11/example_pt2.txt")?.bufferedReader()?.readLines()
    val linesToReadAtATime = 6
    if (lines != null) {
        for (i in 0 .. lines.size / linesToReadAtATime) {
            val round = lines[linesToReadAtATime * i + 0].substringAfter("== After round ").substringBefore(" times.")
            val m1 = lines[linesToReadAtATime * i + 1].substringAfter("inspected items ").substringBefore(" times.").let { Integer.parseInt(it) }
            val m2 = lines[linesToReadAtATime * i + 2].substringAfter("inspected items ").substringBefore(" times.").let { Integer.parseInt(it) }
            val m3 = lines[linesToReadAtATime * i + 3].substringAfter("inspected items ").substringBefore(" times.").let { Integer.parseInt(it) }
            val m4 = lines[linesToReadAtATime * i + 4].substringAfter("inspected items ").substringBefore(" times.").let { Integer.parseInt(it) }
        }

    }

}

fun parseExtreme(resource: String): List<MonkeyExtreme> {
    val lines = {}.javaClass.getResourceAsStream(resource)?.bufferedReader()?.readLines()
    val linesToReadAtATime = 7

    val monkeys: MutableList<MonkeyExtreme> = mutableListOf()
    if (lines != null) {
        for (i in 0 .. lines.size / linesToReadAtATime) {
            val items = parseStartingItems(lines[linesToReadAtATime * i + 1])
            val op = parseExtremeOperation(lines[linesToReadAtATime * i + 2])
            val divisibleTest = Integer.parseInt(parseTest(lines[linesToReadAtATime * i + 3]))
            val monkeyIfTrue = Integer.parseInt(parseTrue(lines[linesToReadAtATime * i + 4]))
            val monkeyIfFalse = Integer.parseInt(parseFalse(lines[linesToReadAtATime * i + 5]))

            monkeys.add(
                MonkeyExtreme(
                    currentItems = items.map { ItemExtreme(it.toLong()) }.toMutableList(),
                    operation = op,
                    divisibleTest = parseExtremeConditions(divisibleTest, monkeyIfTrue, monkeyIfFalse)
                )
            )
        }
    } else {
        throw RuntimeException("Unable to parse")
    }
    return monkeys
}

fun parseExtremeOperation(line: String): (ItemExtreme) -> Long {
    val operationString = line
        .substringAfter("Operation: new = old ")
        .split(" ")

    return when {
        operationString[0] == "+" && isNumber(operationString[1]) -> {
                item: ItemExtreme -> item.worry + operationString[1].toLong()
        }
        operationString[0] == "*" && isNumber(operationString[1]) -> {
                item: ItemExtreme -> item.worry * operationString[1].toLong()
        }
        operationString[0] == "+" -> {
                item: ItemExtreme -> item.worry + item.worry
        }
        operationString[0] == "*" -> {
                item: ItemExtreme -> item.worry * item.worry
        }
        else -> {
            throw RuntimeException("Unexpected operation")
        }
    }
}


fun parseExtremeConditions(divisibleTest: Int, monkeyIfTrue: Int, monkeyIfFalse: Int): (ItemExtreme) -> Int {
    return { item: ItemExtreme->
        if (item.worry.mod(divisibleTest) == 0) {
            monkeyIfTrue
        } else {
            monkeyIfFalse
        }
    }
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
 * to be divided by three and rounded down to the nearest Long.
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

data class MonkeyExtreme(
    val currentItems: MutableList<ItemExtreme>,
    val operation: (ItemExtreme) -> Long,
    val divisibleTest: (ItemExtreme) -> Int,
    var totalInspections: Long = 0
) {
    fun inspectItemsAndPassToOtherMonkeys(
        monkeys: List<MonkeyExtreme>,
        strategy: Int
    ) {
        while (currentItems.isNotEmpty()) {
            val item = currentItems.removeFirst()
            totalInspections++

            val newWorry = this.operation(item)
            item.worry = this.operation(item) / 1
            // strategy
            /*
            if (newWorry < 0) {
                item.worry = item.worry - Integer.MAX_VALUE
                item.worry = this.operation(item)
            } else {
                item.worry = newWorry
            }
            */

            monkeys[divisibleTest(item)].currentItems.add(item)
        }
    }
}

data class ItemExtreme(var worry: Long)
