import java.io.File

val rules = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val corruptPoints = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val patchPoints = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

fun main(args: Array<String>) {
    assert(Pair(false, null) == findCorruption("[({(<(())[]>[[{[]{<()<>>"))
    assert(Pair(true, '}') == findCorruption("{([(<{}[<>[]}>{[]{[(<()>"))
    assert(Pair(true, ']') == findCorruption("{]"))
    assert(Pair(true, ']') == findCorruption("{(]}"))
    assert(Pair(true, null) == findCorruption("({})"))
    assert(Pair(true, null) == findCorruption("()()"))
    assert(Pair(true, null) == findCorruption("((()))"))
    assert(Pair(true, null) == findCorruption("{}"))
    assert(Pair(true, null) == findCorruption("((({}{}[])))"))
    assert("}}]])})]" == patchInvalid("[({(<(())[]>[[{[]{<()<>>"))

    var totalCorruptionPoints = 0
    val patchesPoints = mutableListOf<Long>()

    File(args[0]).forEachLine {
        line ->

        val corruption = findCorruption(line)
        if (corruption.first) { //valid
            if (corruption.second != null) {
                println("CORRUPTED $line ${corruption.second}")
                totalCorruptionPoints += corruptPoints[corruption.second]!!
            } else {
                println("VALID $line")
            }
        } else { //invalid
            val patch = patchInvalid(line)
            println("INVALID $line $patch")
            patchesPoints.add(patch.toCharArray().toList().fold(0) { acc, p -> acc * 5 + patchPoints[p]!!})
        }
    }
    println("result1 $totalCorruptionPoints")
    patchesPoints.sort()
    println("result2 ${patchesPoints[((patchesPoints.size -1) / 2)]}")
}

fun patchInvalid(line: String): String {
    val newLine = cleanAll(line)
    var result = ""
    for (i in newLine.length-1 downTo 0) {
        result += rules[newLine[i]]
    }
    return result
}

fun findCorruption(line: String): Pair<Boolean, Char?> {
    val newLine = cleanAll(line)
    if (newLine.isEmpty()) {
        return Pair(true, null)
    } else {
        for (c in newLine) {
            if (c in rules.values) {
                return Pair(true, c)
            }
        }

        return Pair(false, null)
    }
}

fun cleanAll(line: String): String {
    var lineA = line
    var lineB = clean(lineA)
    while (lineB.length < lineA.length) {
        lineA = lineB
        lineB = clean(lineA)
    }
    return lineB
}

private fun clean(line: String): String {
    if (line.length > 1) {
        for (i in 0..line.length-2) {
            if (rules[line[i]] == line[i+1]) {
                return line.substring(0, i ) + line.substring(i+2)
            }
        }
    }
    return line
}
