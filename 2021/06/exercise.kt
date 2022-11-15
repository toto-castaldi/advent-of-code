import java.io.File
import java.util.Arrays
import java.util.Collections

fun main(args: Array<String>) {
    val firstLine: String? = File("input.txt").useLines { it.firstOrNull() } 
    if (firstLine != null) {
        var initialState: List<Int> = firstLine.split(",").map({it.trim().toInt()})
        println(initialState)
        val days: Int = args[0].toInt()
        println(days)
        for (i in 1..days) {
            val newState: ArrayList<Int> = ArrayList()
            var newFishCount : Int = 0
            for (theState in initialState) {
                var state : Int
                if (theState == 0) {
                    state = 6
                    newFishCount ++
                } else {
                    state = theState -1
                }
                newState.add(state)
            }
            for (nfc in 1..newFishCount) {
                newState.add(8)
            }
            initialState = ArrayList()
            initialState.addAll(newState)
            println(initialState)
        }

        val result : Int = initialState.size
        println("result $result")

    }
}