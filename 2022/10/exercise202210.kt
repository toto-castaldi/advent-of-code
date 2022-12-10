import java.io.File

private class Computer(val lines: List<String>) {
    private val registres = mutableMapOf<String, Long>()
    private var tick = 0
    private var currentInstructionIndex = 0
    private var instructionTick = 0

    fun addRegistry(registryName: String, value: Long): Long {
        registres[registryName] = registres[registryName] ?.let { it + value } ?: 0 + value
        return registres[registryName]!!
    }

    fun executionContinues(): Boolean {
        return currentInstructionIndex < lines.size
    }

    fun tick(): Int {
        tick ++
        instructionTick ++

        return tick
    }

    private fun parse(line: String): Instruction {
        val definition = instructionsSet[line.substring(0,4)]!!
        val value = if (line.length > 4) line.substring(5).toLong() else 0L
        return Instruction(definition, value)
    }

    fun getCurrentInstruction(): Instruction? {
        val instruction = parse(lines[currentInstructionIndex])
        if (instruction.definition.cycles == instructionTick) {
            currentInstructionIndex ++
            instructionTick = 0
            return instruction
        }
        return null
    }

    fun getRegistryValue(registryName: String): Long {
        return registres[registryName]!!
    }

}
private data class InstructionDefinition(val name: String, val cycles : Int, val execution : (Computer, Long) -> Unit)

private data class Instruction(val definition: InstructionDefinition, val value: Long)

private val instructionsSet = mapOf<String, InstructionDefinition>(
    "noop" to InstructionDefinition("noop", 1) { _, _ -> {}},
    "addx" to InstructionDefinition("addx", 2) { memory, arg ->
        memory.addRegistry("x", arg)
    }
)

fun main(
    args: Array<String>
) {
    val computer = Computer(File(args[0]).readLines())
    computer.addRegistry("x", 1)
    val signalStrengts = mutableMapOf<Int,Long>(
        20 to 0L,
        60 to 0L,
        100 to 0L,
        140 to 0L,
        180 to 0L,
        220 to 0L
    )

    while (computer.executionContinues()) {
        val currentTick = computer.tick()

        if (currentTick in signalStrengts.keys) {
            signalStrengts[currentTick] = currentTick * computer.getRegistryValue("x")!!
        }

        val instruction = computer.getCurrentInstruction()
        if (instruction != null) {
            instruction.definition.execution(computer, instruction.value)
            println("[$currentTick] (${computer.getRegistryValue("x")}) ${instruction!!.definition.name} ${instruction!!.value}")
        } else {
            println("[$currentTick] (${computer.getRegistryValue("x")})")
        }


    }


    println(signalStrengts)
    println(signalStrengts.values.fold(0L) { acc, value -> acc + value})
}
