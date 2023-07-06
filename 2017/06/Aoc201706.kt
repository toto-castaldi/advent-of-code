import java.io.File

class Aoc201706() {

    private val memory = mutableListOf<Int>()
    private val finegerprintHistory = mutableListOf<String>()

    operator fun plus(value: Int) {
        memory.add(value)
    }

    fun redistributionCountBeforeLoop(): Int {
        var count = 0
        var memoryFingerPrint = ""

        println(memory)

        while (memoryFingerPrint !in finegerprintHistory) {
            finegerprintHistory.add(memoryFingerPrint)

            redistributeMemoryBlocks()

            memoryFingerPrint = computeFingerPrint()

            count += 1
        }

        return count
    }

    private fun redistributeMemoryBlocks() {
        //find FIRST max block
        var maxBlockSize = -1
        val sizeToIndex = mutableMapOf<Int, Int>()
        for ((blockIndex, blockSize) in memory.withIndex()) {
            if (blockSize > maxBlockSize) {
                maxBlockSize = blockSize
            }
            if (blockIndex !in sizeToIndex.values) {
                sizeToIndex[blockSize] = blockIndex
            }
        }
        val blockIndex = sizeToIndex[maxBlockSize]!!
        println("found block with $maxBlockSize at pos $blockIndex")

        //erase blank
        memory[blockIndex] = 0

        for (i in 1..maxBlockSize) {
            memory[(blockIndex + i) % memory.size ] += 1
        }

        println(memory)
    }

    private fun computeFingerPrint(): String {
        var result = ""
        for (m in memory) {
            result += m.toString().padStart(5, '0')
        }

        println("fingerprint $result")

        return result
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201706()
            for (i in File(fileName).readText().split(" ")) {
                aoc + i.trim().toInt()
            }
            println(aoc.redistributionCountBeforeLoop())
        }
    }


}
