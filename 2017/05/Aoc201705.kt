import java.io.File

class Aoc201705() {

    private val instructions = mutableListOf<Int>()

    operator fun plus(value: Int) {
        instructions.add(value)
    }


    fun stepToExit(): Int {
        var currentPos = 0
        var executionCount = 0
        while (currentPos < instructions.size) {
            val nextJump = instructions[currentPos]
            instructions[currentPos] ++
            currentPos += nextJump
            executionCount ++
        }

        return executionCount
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
