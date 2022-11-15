import java.io.File
import java.util.Arrays
import java.util.Collections

fun main(args: Array<String>) {
    val firstLine: String? = File("input.txt").useLines { it.firstOrNull() } 
    if (firstLine != null) {
        var theState: HashMap<Int, Long> = HashMap()
        var inputState: List<Int> = firstLine.split(",").map({it.trim().toInt()})
        println(inputState)
        val days: Int = args[0].toInt()

        for (s in inputState) {
            theState.put(s, theState.getOrDefault(s, 0) + 1)
        }
        println(theState)

        for (i in 1..days) {
            println(i)
            var newFishCount : Long = 0
            val newState: HashMap<Int, Long> = HashMap()
            for (s in theState.keys) {
                var state : Int
                if (s == 0) {
                    state = 6
                    newFishCount += theState.get(s)!!
                } else {
                    state = s -1
                }
                newState.put(state, newState.getOrDefault(state, 0) + theState.get(s)!!)
                
            }
            newState.put(8, newState.getOrDefault(8, 0) + newFishCount)
            println(newState)
            for (k in 0..8) {
                theState.remove(k)
            }
            theState.putAll(newState)
        }

        var result : Long = 0
        for (v in theState.values) {
            result += v
        }
        println("result $result")

    }
}