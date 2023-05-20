import java.io.File

class Aoc201705() {

    operator fun plus(value: Int) {
        TODO("Not yet implemented")
    }


    fun stepToExit(): Int {
        TODO("Not yet implemented")
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201705()
            File(fileName).forEachLine {line ->
                aoc + line.trim().toInt()
            }
            println( aoc.stepToExit())
        }

    }

}
