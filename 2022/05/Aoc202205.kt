import java.io.File

class Aoc202205(private val fileName: String) {
    fun run(print : Boolean = false) {
        val lines = File(fileName).readLines()
        val stackNumbers = lines.find { it.startsWith(" 1") }!!
        val stackCount = stackNumbers.split("  ").map { it.trim().toInt() }.last()
        val stackStartingDescriptionCount = lines.indexOf(stackNumbers)
        val stacks1 = List(stackCount) { mutableListOf<Char>() }
        val stacks2 = List(stackCount) { mutableListOf<Char>() }
        var i = 0
        while (i < stackStartingDescriptionCount) {
            val stackStartingDescription = lines[i++]
            val chunks = stackStartingDescription.chunked(4)
            for (j in chunks.indices) {
                val element = chunks[j].trim().substringAfter('[').substringBefore(']')
                if (element.isNotEmpty()) {
                    stacks1[j].add(element[0])
                    stacks2[j].add(element[0])
                }
            }
        }
        for (j in stackStartingDescriptionCount + 2 until lines.size) {
            val movingLine = lines[j]
            val match = "move (\\d+) from (\\d+) to (\\d+)".toRegex().find(movingLine)
            val howMany = match!!.groups[1]!!.value.toInt()
            val fromStack = match.groups[2]!!.value.toInt() - 1
            val toStack = match.groups[3]!!.value.toInt() - 1
            if (print) println("move $howMany from $fromStack to $toStack")
            val movingElements1 = mutableListOf<Char>()
            val movingElements2 = mutableListOf<Char>()
            for (k in 1..howMany) {
                movingElements1.add(stacks1[fromStack].removeFirst())
                movingElements2.add(stacks2[fromStack].removeFirst())
            }
            stacks1[toStack].addAll(0, movingElements1.reversed())
            stacks2[toStack].addAll(0, movingElements2)
        }
        println(stacks1.fold("") { acc, elements -> (acc + elements.first())})
        println(stacks2.fold("") { acc, elements -> (acc + elements.first())})
    }
}

