import java.io.File

fun main(args: Array<String>) {
    val lines = File(args[0]).readLines()
    val stackNumbers = lines.find { it.startsWith(" 1")}!!
    val stackCount = stackNumbers.split("  ").map { it.trim().toInt() }.last()
    val stackStartingDescriptionCount = lines.indexOf(stackNumbers)
    val stacks1 = buildList(stackCount) {
        for (i in 1..stackCount) {
            add(mutableListOf<Char>())
        }
    }
    val stacks2 = buildList(stackCount) {
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
                stacks1[j].add(element[0])
                stacks2[j].add(element[0])
            }
        }
    }
    for (i in stackStartingDescriptionCount + 2..lines.size-1) {
        val movingLine = lines[i]
        val howMany = movingLine.substringAfter("move ").substringBefore(" from").trim().toInt()
        val fromStack = movingLine.substringAfter(" from ").substringBefore(" to").trim().toInt()-1
        val toStack = movingLine.substringAfter(" to ").trim().toInt()-1
        println("move $howMany from $fromStack to $toStack")
        val movingElements1 = mutableListOf<Char>()
        val movingElements2 = mutableListOf<Char>()
        for (j in 1 .. howMany) {
            movingElements1.add(stacks1[fromStack].removeFirst())
            movingElements2.add(stacks2[fromStack].removeFirst())
        }
        stacks1[toStack].addAll(0, movingElements1.reversed())
        stacks2[toStack].addAll(0, movingElements2)
    }
    println(stacks1.fold("") { acc, elements -> acc + if (elements.firstOrNull() != null) elements.firstOrNull()!! else ""})
    println(stacks2.fold("") { acc, elements -> acc + if (elements.firstOrNull() != null) elements.firstOrNull()!! else ""})

}

