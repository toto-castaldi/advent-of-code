import java.io.File
import java.util.Arrays
import java.util.Collections

fun totalFuel1(elements : List<Int>, destination : Int) : Int {
    var result = 0;
    for (e in elements) {
        //println("from $e to $destination")
        result += Math.abs(destination - e)
    }
    //println(result)
    return result
}

fun totalFuel2(elements : List<Int>, destination : Int) : Int {
    var result = 0;
    
    for (e in elements) {
        var cost = 1
        val distance = Math.abs(destination - e)
        for (i in 1..distance) {
            result += cost
            cost ++
        }
    }
    //println(result)
    return result
}

fun main() {
    
    val firstLine: String? = File("input.txt").useLines { it.firstOrNull() } 
    if (firstLine != null) {
        val positions: List<Int> = firstLine.split(",").map({it.trim().toInt()})
        println(positions)
        val sortedPositions : List<Int> = positions.sorted()
        println(sortedPositions)
        println(sortedPositions.size)
        var index1 : Int? 
        var index2 : Int? 
        if (sortedPositions.size % 2 == 0) { //even
            index1 = Math.ceil(sortedPositions.size.toDouble() / 2).toInt() - 1
            index2 = Math.floor(sortedPositions.size.toDouble() / 2).toInt() 
        } else {
            index1 = Math.floor(sortedPositions.size.toDouble() / 2).toInt() 
            index2 = index1
        }
        println("$index1, $index2")
        var res1 = totalFuel1(positions, sortedPositions[index1])
        var res2 = totalFuel1(positions, sortedPositions[index2])
        println("$res1, $res2")

        val sumPositions = positions.fold(0) { acc, value -> acc + value }
        val meanPosition : Int = Math.ceil(sumPositions.toDouble() / positions.size.toDouble()).toInt()

        println(meanPosition)

        var resStep2 = totalFuel2(positions, meanPosition)
        println("$resStep2")
    }
}