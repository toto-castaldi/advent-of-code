import java.io.File
import java.util.ArrayList

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput = File(fileName)
    var levels : ArrayList<Int> = ArrayList()
    var size : Int = 0
    fileInput.forEachLine {
        line ->

        size = line.length
        levels.addAll(line.toCharArray().toList().map {
            c ->

            c.digitToInt()
        })


    }
    println(levels)

    var total : Int = 0
    for ((i, value) in levels.iterator().withIndex()) {
        val dx : Int ? = if ((i < levels.size -1) && ((i + 1) % size != 0)) levels[i+1] else null
        val sx : Int ? = if ((i > 0) && (i% size != 0)) levels[i-1] else null
        val dw : Int ? = if (i + size < levels.size) levels[i+size] else null
        val up : Int ? = if (i - size >= 0) levels[i-size] else null

        if (
            (value < sx ?: Int.MAX_VALUE) &&
            (value < dx ?: Int.MAX_VALUE) &&
            (value < dw ?: Int.MAX_VALUE) &&
            (value < up ?: Int.MAX_VALUE)
        ) {
            total += value
            println(value)
        }
    }
    println("result $total")

}
