import java.io.File
import java.util.Arrays

fun main() {
    val firstLine: String? = File("input.txt").useLines { it.firstOrNull() } 
    if (firstLine != null) {
        var counters: Array<Int> = Array(firstLine.length) { 0 }
        var lineCount : Int = 0
        File("input.txt").forEachLine { 
            lineCount ++
            for (i in 0 until it.length) {
                counters[i] += it[i].digitToInt()
            }
        }
        println(Arrays.toString(counters))
        var gamma : String = ""
        var epsilon : String = ""
        for (i in 0..counters.size-1) {
            val ones = counters[i]
            if (lineCount - ones > lineCount / 2) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }
        val gammaVal : Int = Integer.parseInt(gamma, 2);
        val epsilonVal : Int = Integer.parseInt(epsilon, 2);
        val result : Int = gammaVal * epsilonVal
        println("g $gammaVal, e $epsilonVal, res $result")
    }
}