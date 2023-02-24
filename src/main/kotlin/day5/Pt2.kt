package day5

import java.util.Stack

fun main() {

    val lines = {}.javaClass.getResourceAsStream("/day5/input.txt")?.bufferedReader()?.readLines()
    val indexOfInstructionSeparator = lines?.indexOf("")
    val sizeOfArray = lines?.get(0)?.length?.div(4)?.plus(1)
    val stacks: Array<Stack<Char>>? = sizeOfArray?.let { Array(it, init = { Stack<Char>() }) }
    lines?.forEachIndexed { lineNumber, line ->
        /**
         * 1. Need to parse initial stacks
         * 2. Read instructions of where/how many to pop and where/how many to push
         */
        if (lineNumber < indexOfInstructionSeparator!!) {
            line.toCharArray().forEachIndexed { charIndex, c ->
                if (charIndex % 4 == 1 && Character.isLetter(c)) {
                    val stackIndex = charIndex / 4
                    stacks?.get(stackIndex)?.add(0, c) // the top input lines is at the end of the stack
                }
            }
        } else if (lineNumber > indexOfInstructionSeparator) {
            /**
             * [0] - quantity
             * [1] - from where
             * [2] - to where
             */
            val directions = line.split(" ")
                .filter { isNumber(it) }
                .map { Integer.parseInt(it) }

            val stackSource = stacks?.get(directions[1] - 1)
            val stackDestination = stacks?.get(directions[2] - 1)
            val tempStack = Stack<Char>()
            for (i in 0 until directions[0]) {
                stackSource?.pop()?.let { tempStack.add(it) }
            }
            while (tempStack.isNotEmpty()) {
                stackDestination?.add(tempStack.pop())
            }
        }
    }
    stacks?.forEach {
        print(it.peek())
    }
}
