import java.io.File

fun main(args: Array<String>) {
    val symbolMap = mapOf("A" to "R", "B" to "P", "C" to "S", "X" to "R", "Y" to "P", "Z" to "S")
    val playerResults = mapOf("RS" to 0, "SP" to 0, "PR" to 0, "SR" to 6, "PS" to 6, "RP" to 6, "SS" to 3, "PP" to 3, "RR" to 3)
    val chooseValues = mapOf("R" to 1, "P" to 2, "S" to 3)
    val winningMoves = mapOf("P" to "S", "R" to "P", "S" to "R")
    val losingMoves = mapOf("S" to "P", "P" to "R", "R" to "S")
    var totalScore1 = 0
    var totalScore2 = 0

    File(args[0]).forEachLine { line ->
        val (handFirst, handSecond) = line.trim().split(" ")
        val first = symbolMap[handFirst]
        var second = symbolMap[handSecond]

        totalScore1 += chooseValues[second]!! + playerResults["$first$second"]!!

        when (handSecond) {
            "X" -> second = losingMoves[first]
            "Z" -> second = winningMoves[first]
            else -> {
                second = first
            }
        }

        totalScore2 += chooseValues[second]!! + playerResults["$first$second"]!!
    }

    println("result1 $totalScore1")
    println("result2 $totalScore2")
}
