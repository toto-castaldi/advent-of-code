import java.io.File

fun main(args: Array<String>) {
    val symbolMap = mapOf("A" to "R", "B" to "P", "C" to "S", "X" to "R", "Y" to "P", "Z" to "S")
    val playerResults = mapOf("RS" to 0, "SP" to 0, "PR" to 0, "SR" to 6, "PS" to 6, "RP" to 6, "SS" to 3, "PP" to 3, "RR" to 3)
    val chooseValues = mapOf("R" to 1, "P" to 2, "S" to 3)
    var totalScore = 0

    File(args[0]).forEachLine { line ->
        val hand = line.trim().split(" ")
        val first = symbolMap[hand[0]]
        val second = symbolMap[hand[1]]

        totalScore += chooseValues[second]!! + playerResults["$first$second"]!!
    }

    println(totalScore)


}
