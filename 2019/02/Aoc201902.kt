import java.io.File

class Aoc201902() {

    fun parse(program: String) : MutableList<Int> {
        return program.split(',').map { it.trim().toInt() }.toMutableList()
    }

    fun execute(program: String): List<Int> {
        val memory = parse(program)
        return execute(memory)
    }

    fun execute(memory: MutableList<Int>) : MutableList<Int> {
        var programIndex = 0
        while (memory[programIndex] != 99) {
            when (memory[programIndex]) {
                1 -> memory[memory[programIndex + 3]] = memory[memory[programIndex + 1]] + memory[memory[programIndex + 2]]
                2 -> memory[memory[programIndex + 3]] = memory[memory[programIndex + 1]] * memory[memory[programIndex + 2]]
            }
            programIndex += 4
        }
        return memory
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201902()
            val program = File(fileName).readText()
            val memory = aoc.parse(program)
            memory[1] = 12
            memory[2] = 2
            println(aoc.execute(memory)[0])
        }
    }


}
