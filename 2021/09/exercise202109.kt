import java.io.File
import java.util.ArrayList

fun main(args: Array<String>) {
    val fileName : String = args[0]
    val fileInput = File(fileName)
    val levels : ArrayList<Pair<Int, Boolean>> = ArrayList()
    var size = 0
    fileInput.forEachLine {
        line ->

        size = line.length
        levels.addAll(line.toCharArray().toList().map {
            c ->

            Pair(c.digitToInt(), false)
        })
    }
    println(levels)

    var total1 = 0
    for ((i, pair) in levels.iterator().withIndex()) {
        val value = pair.first
        val dx : Int ? = if ((i < levels.size -1) && ((i + 1) % size != 0)) levels[i+1].first else null
        val sx : Int ? = if ((i > 0) && (i% size != 0)) levels[i-1].first else null
        val dw : Int ? = if (i + size < levels.size) levels[i+size].first else null
        val up : Int ? = if (i - size >= 0) levels[i-size].first else null

        if (
            (value < (sx ?: Int.MAX_VALUE)) &&
            (value < (dx ?: Int.MAX_VALUE)) &&
            (value < (dw ?: Int.MAX_VALUE)) &&
            (value < (up ?: Int.MAX_VALUE))
        ) {
            total1 += value + 1
            val ssx = sx?.toString() ?: " "
            val sdx = dx?.toString() ?: " "
            val sdw = dw?.toString() ?: " "
            val sup = up?.toString() ?: " "

            println(" $sup")
            println("$ssx$value$sdx")
            println(" $sdw")
            println("##### $value -> $total1")

        }
    }
    println("result1 $total1")

}
