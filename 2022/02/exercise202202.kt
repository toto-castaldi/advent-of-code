import java.io.File

fun main(args: Array<String>) {
    val symbolMap = mapOf("A" to "R", "B" to "P", "C" to "S", "X" to "R", "Y" to "P", "Z" to "S")
    val playerResults = mapOf("RS" to 0, "SP" to 0, "PR" to 0, "SR" to 6, "PS" to 6, "RP" to 6, "SS" to 3, "PP" to 3, "RR" to 3)
    val chooseValues = mapOf("R" to 1, "P" to 2, "S" to 3)
    var totalScore1 = 0
    var totalScore2 = 0

    File(args[0]).forEachLine { line ->
        val hand = line.trim().split(" ")
        val handFirst = hand[0]
        val handSecond = hand[1]
        var first = symbolMap[handFirst]
        var second = symbolMap[handSecond]

        totalScore1 += chooseValues[second]!! + playerResults["$first$second"]!!

        if (handSecond == "X") { 
            if (first == "S")
                second = "P"
            if (first == "P")
                second = "R"
            if (first == "R")
                second = "S"
        }
        if (handSecond == "Z") {
            if (first == "P")
                second = "S"
            if (first == "R")
                second = "P"
            if (first == "S")
                second = "R"
        }
        if (handSecond == "Y")
            second = first

        totalScore2 += chooseValues[second]!! + playerResults["$first$second"]!!
    }

    println("result1 $totalScore1")
    println("result2 $totalScore2")


}
