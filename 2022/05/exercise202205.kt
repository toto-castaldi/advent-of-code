import java.io.File

fun main(args: Array<String>) {
    val lines = File(args[0]).readLines()
    val stackNumbers = lines.find { it.startsWith(" 1")}!!
    val stackCount = stackNumbers.split("  ").map { it.trim().toInt() }.last()
    val stackStartingDescriptionCount = lines.indexOf(stackNumbers)
    val stacks = buildList(stackCount) {
        for (i in 1..stackCount) {
            add(mutableListOf<Char>())
        }
    }
    var i = 0
    while (i < stackStartingDescriptionCount) {
        val stackStartingDescription = lines[i++]
        val chunks = stackStartingDescription.chunked(4)
        for (j in 0..chunks.size-1) {
            val element = chunks[j].trim().substringAfter('[').substringBefore(']')
            if (element.isNotEmpty()) {
                stacks[j].add(element[0])
            }
        }
    }
    for (i in stackStartingDescriptionCount + 2..lines.size-1) {
        val movingLine = lines[i]
        val howMany = movingLine.substringAfter("move ").substringBefore(" from").trim().toInt()
        val fromStack = movingLine.substringAfter(" from ").substringBefore(" to").trim().toInt()-1
        val toStack = movingLine.substringAfter(" to ").trim().toInt()-1
        println("move $howMany from $fromStack to $toStack")
        val movingElements = mutableListOf<Char>()
        for (j in 1 .. howMany) {
            movingElements.add(stacks[fromStack].removeFirst())
        }
        stacks[toStack].addAll(0, movingElements.reversed())
    }
    println(stacks.fold("") { acc, elements -> acc + if (elements.firstOrNull() != null) elements.firstOrNull()!! else ""})

}

