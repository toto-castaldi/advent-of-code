import java.io.File

val rules = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

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

    var corruptionPoints = 0
    File(args[0]).forEachLine {
        line ->

        val corruption = findCorruption(line)
        if (corruption.first) { //valid
            println("$line ${corruption.second}")
            corruptionPoints += points[corruption.second]!!
        }
    }
    println("$corruptionPoints")
}

fun findCorruption(line: String): Pair<Boolean, Char?> {
    var lineA = line
    var lineB = clean(lineA)
    while (lineB.length < lineA.length) {
        lineA = lineB
        lineB = clean(lineA)
    }
    if (lineB.isEmpty()) {
        return Pair(true, null)
    } else {
        for (c in lineB) {
            if (c in rules.values) {
                return Pair(true, c)
            }
        }

        return Pair(false, null)
    }
}

fun clean(line: String): String {
    if (line.length > 1) {
        for (i in 0..line.length-2) {
            if (rules[line[i]] == line[i+1]) {
                return line.substring(0, i ) + line.substring(i+2)
            }
        }
    }
    return line
}
