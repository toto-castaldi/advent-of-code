import java.io.File

class Aoc201705() {

    private val instructions = mutableListOf<Int>()

    operator fun plus(value: Int) {
        instructions.add(value)
    }

    fun stepToExitRule(increaseDecrease : Boolean): Int {
        var currentPos = 0
        var executionCount = 0
        while (currentPos < instructions.size) {
            val nextJump = instructions[currentPos]
            if (instructions[currentPos] >= 3 && increaseDecrease) {
                instructions[currentPos]--
            } else {
                instructions[currentPos]++
            }
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
            println( aoc.stepToExitRule(false))
        }

        fun run2(fileName: String) {
            val aoc = Aoc201705()
            File(fileName).forEachLine {line ->
                aoc + line.trim().toInt()
            }
            println( aoc.stepToExitRule(true))
        }

    }

}
