import java.io.File

fun main(args: Array<String>) {
    val symbols = mapOf("A" to "R", "B" to "P", "C" to "S", "X" to "R", "Y" to "P", "Z" to "S")
    val playingPoints = mapOf("RS" to 0, "SP" to 0, "PR" to 0, "SR" to 6, "PS" to 6, "RP" to 6, "SS" to 3, "PP" to 3, "RR" to 3)
    val choosingPoints = mapOf("R" to 1, "P" to 2, "S" to 3)
    val winningMoves = mapOf("P" to "S", "R" to "P", "S" to "R")
    val losingMoves = winningMoves.entries.associate{(k,v)-> v to k}
    var totalScore1 = 0
    var totalScore2 = 0

    File(args[0]).forEachLine { line ->
        val (handFirst, handSecond) = line.trim().split(" ")
        val first = symbols[handFirst]
        var second = symbols[handSecond]

        totalScore1 += choosingPoints[second]!! + playingPoints["$first$second"]!!

        second = when (handSecond) {
            "X" -> losingMoves[first]
            "Z" -> winningMoves[first]
            else -> {
                first
            }
        }

        totalScore2 += choosingPoints[second]!! + playingPoints["$first$second"]!!
    }

    println("result1 $totalScore1")
    println("result2 $totalScore2")
}
