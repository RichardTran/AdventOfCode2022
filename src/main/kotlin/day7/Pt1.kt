package day7

fun main() {
    val headNode = {}.javaClass.getResourceAsStream("/day7/input.txt")
        ?.bufferedReader()
        ?.readLines()
        ?.let { parseIntoTree(it) }

    if (headNode != null) {
        println(calculateSumOfDirectories(headNode))
        prettyPrintTree(headNode)
    }
}

private fun parseIntoTree(lines: List<String>): Node? {
    var head: Node? = null
    var ptr: Node? = null
    lines.forEach { line ->
        val parsedLine = line.split(" ")
        if (parsedLine[0] == "$") { // commands
            if (head == null && parsedLine[1] == "cd" && parsedLine[2] == "/") { // initialize
                head = Node(name = parsedLine[2], details = NodeDetails(isFile = false))
                ptr = head
            } else if (parsedLine[1] == "cd") {
                ptr = if (parsedLine[2] == "..") ptr?.parent else ptr?.children?.get(parsedLine[2])
            }
            // ignore ls
        } else {
            if (parsedLine[0] == "dir") {
                ptr?.children?.set(
                    parsedLine[1],
                    Node(parent = ptr, name = parsedLine[1], details = NodeDetails(isFile = false))
                )
            } else {
                ptr?.children?.set(
                    parsedLine[1],
                    Node(
                        parent = ptr,
                        name = parsedLine[1],
                        details = NodeDetails(isFile = true, size = Integer.parseInt(parsedLine[0]))
                    )
                )
            }
        }
    }
    return head
}

private fun calculateSumOfDirectories(head: Node): Int {
    calculateSizes(head)
    return sumSizesOfDirectories(head)
}

/**
 * Recursively go down the tree and determines sizes of dirs and files
 * If it's a file, then the size is itself.
 * If it's a dir, then the size is the sum of its children
 */
private fun calculateSizes(head: Node): Int {
    val sum = if (head.details.isFile) {
        head.details.size
    } else {
        head.children.values.sumOf {
            calculateSizes(it)
        }
    }
    head.details.size = sum
    return sum
}

private fun sumSizesOfDirectories(head: Node): Int {
    var sum = head.children.values
        .filter { it.details.isNotFile }
        .sumOf {
        sumSizesOfDirectories(it)
    }
    if (head.details.size <= 100000)
        sum += head.details.size

    return sum
}

/**
 * Pretty print tree for troubleshooting
 */
private fun prettyPrintTree(head: Node) {
    prettyPrintTree(head, tabCount = 0)
}

private fun prettyPrintTree(head: Node, tabCount: Int) {
    for (i in 0 until tabCount) {
        print("--/")
    }
    println("${head.name}${if (head.details.isFile) " - (${head.details.size} KB)" else "(${head.details.size} KB directory)"}")
    head.children.values.forEach {
        prettyPrintTree(it, tabCount + 1)
    }
}

data class Node constructor(
    var name: String,
    var parent: Node? = null,
    var children: MutableMap<String, Node> = mutableMapOf(),
    var details: NodeDetails
)

data class NodeDetails constructor(val isFile: Boolean, var size: Int = 0) {
    val isNotFile = !isFile
}
