import java.io.File
import java.util.Arrays
import java.util.Collections

fun computeCounters(values: List<String>): Array<Int> {
    val counters: Array<Int> = Array(values.get(0).length) { 0 }
    for (it in values) {
        for (i in 0 until it.length) {
            counters[i] += it[i].digitToInt()
        }
    }
    return counters;
}

fun main() {
    val firstLine: String? = File("input.txt").useLines { it.firstOrNull() } 
    var oxigenValues: List<String> = List(0) { "" }
    var co2Values: List<String> = List(0) { "" }
    var values: List<String> = List(0) { "" }
    if (firstLine != null) {
        File("input.txt").forEachLine { 
            values = values.plusElement(it)
            oxigenValues = oxigenValues.plusElement(it)
            co2Values = co2Values.plusElement(it)
        }
        var counters = computeCounters(values);
        var gamma : String = ""
        var epsilon : String = ""
        for (i in 0..counters.size-1) {
            val ones = counters[i]
            if (ones >= values.size - ones) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }

        var i = 0
        while (oxigenValues.size > 1) {
            counters = computeCounters(oxigenValues);
            val ones = counters[i]
            val zeros = oxigenValues.size - counters[i]
            if (ones >= zeros) { 
                oxigenValues = oxigenValues.filter { it[i] == '1' }
            } else { 
                oxigenValues = oxigenValues.filter { it[i] == '0' }
            }
            i ++
        }

        i = 0
        while (co2Values.size > 1) {
            counters = computeCounters(co2Values);
            val ones = counters[i]
            val zeros = co2Values.size - counters[i]
            if (ones >= zeros) { 
                co2Values = co2Values.filter { it[i] == '0' }
            } else { 
                co2Values = co2Values.filter { it[i] == '1' }
            }
            i ++
        }

        val gammaVal : Int = Integer.parseInt(gamma, 2);
        val epsilonVal : Int = Integer.parseInt(epsilon, 2);
        val result1 : Int = gammaVal * epsilonVal
        val oxigen : Int = Integer.parseInt(oxigenValues[0], 2);
        val co2 : Int = Integer.parseInt(co2Values[0], 2);
        val result2 : Int = oxigen * co2
        println("g $gammaVal, e $epsilonVal, res1 $result1, o $oxigen, co2 $co2, res2 $result2 ")
    }
}