import java.io.File

class Aoc202210(private val fileName: String) {


    private class Computer(val lines: List<String>) {
        private val memory = mutableMapOf<String, Long>()
        private var tick = 0
        private var currentInstructionIndex = 0
        private var instructionTick = 0

        fun addRegistry(registryName: String, value: Long): Long {
            memory[registryName] = memory[registryName]?.let { it + value } ?: (0 + value)
            return memory[registryName]!!
        }

        fun executionContinues(): Boolean {
            return currentInstructionIndex < lines.size
        }

        fun tick(): Int {
            tick++
            instructionTick++

            return tick
        }

        private val instructionsSet = mapOf<String, InstructionDefinition>(
            "noop" to InstructionDefinition("noop", 1) { _, _ -> run {} },
            "addx" to InstructionDefinition("addx", 2) { memory, arg ->
                memory.addRegistry("x", arg)
            }
        )

        private fun parse(line: String): Instruction {
            val definition = instructionsSet[line.substring(0, 4)]!!
            val value = if (line.length > 4) line.substring(5).toLong() else 0L
            return Instruction(definition, value)
        }

        fun getCurrentInstruction(): Instruction? {
            val instruction = parse(lines[currentInstructionIndex])
            if (instruction.definition.cycles == instructionTick) {
                currentInstructionIndex++
                instructionTick = 0
                return instruction
            }
            return null
        }

        fun getRegistryValue(registryName: String): Long {
            return memory[registryName]!!
        }

    }

    private data class InstructionDefinition(val name: String, val cycles: Int, val execution: (Computer, Long) -> Unit)

    private data class Instruction(val definition: InstructionDefinition, val value: Long)



    private class CRT(val length: Int, height: Int) {
        private val panel: List<MutableList<Char>>

        init {
            panel = (1..height).map { (1..length).map { ' ' }.toMutableList() }
        }

        fun cycle(tick: Int, x: Long) {
            val range = x - 1..x + 1
            val row = (tick - 1) / length
            val pixel = tick - 1 - row * length
            if (range.contains(pixel)) {
                panel[row][pixel] = '#'
            }
        }

        override fun toString(): String {
            return panel.fold("") { acc0, value0 ->
                acc0 + value0.fold("") { acc1, value1 -> acc1 + value1 } + "\n"
            }
        }
    }

    fun run(
    ) {
        val cpu = Computer(File(fileName).readLines())
        val monitor = CRT(40, 6)
        cpu.addRegistry("x", 1)
        var signalStrengts = 0L

        while (cpu.executionContinues()) {
            val currentTick = cpu.tick()

            if ((currentTick - 20).mod(40) == 0 && currentTick <= 220) {
                signalStrengts += currentTick * cpu.getRegistryValue("x")
            }

            monitor.cycle(currentTick, cpu.getRegistryValue("x"))

            val instruction = cpu.getCurrentInstruction()
            if (instruction != null) {
                instruction.definition.execution(cpu, instruction.value)
                println("[$currentTick] (${cpu.getRegistryValue("x")}) ${instruction.definition.name} ${instruction.value}")
            } else {
                println("[$currentTick] (${cpu.getRegistryValue("x")})")
            }

        }

        println(signalStrengts)
        println(monitor)
    }
}
