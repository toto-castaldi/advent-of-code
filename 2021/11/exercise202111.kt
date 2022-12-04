import java.io.File

private class Octopus(val energy: Int) {

    private var neighbors: MutableMap<Coordinates, Octopus>
    private var currentyEnergy: Int

    init {
        currentyEnergy = energy
        neighbors = mutableMapOf<Coordinates, Octopus>()
    }

    fun neighbor(x: Int, y: Int, neighbor: Octopus) {
        neighbors[Coordinates(x, y)] = neighbor
    }

    fun resolve(stepX: Int, stepY: Int): Octopus? {
        var result: Octopus? = this
        if (stepX != 0) {
            val foot = if (stepX > 0) 1 else -1
            for (i in 1..stepX) {
                if (result != null) {
                    result = result.resolve(foot, 0)
                }
            }
        }
        if (stepY != 0) {
            val foot = if (stepY > 0) 1 else -1
            for (i in 1..stepY) {
                if (result != null) {
                    result = result.resolve(0, foot)
                }
            }
        }

        return result
    }
}

private data class Coordinates(val x: Int, val y: Int)

fun main(args: Array<String>) {
    var octopusPointer: Octopus? = null
    val matrix = File(args[0]).readLines().map { line -> line.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    for (y in 0 until matrix.size) {
        for (x in 0 until matrix[y].size) {
            val octopus = Octopus(matrix[y][x])
            if (x == 0) {
                if (octopusPointer != null) {
                    octopus.neighbor(0, -1, octopusPointer.resolve(-matrix[y].size-1, 0)!!)
                }
            } else {
                octopus.neighbor(-1, 0, octopusPointer!!)
                if (y > 0) {
                    val firstPrevRow = octopusPointer.resolve(-matrix[y].size-1, 0)!!
                    octopus.neighbor(-1, -1, firstPrevRow)
                    octopus.neighbor(0, -1, firstPrevRow.resolve(1, 0)!!)
                }
            }
            octopusPointer = octopus
        }
    }
    octopusPointer = octopusPointer!!.resolve(matrix[0].size-1, matrix.size -1)

    while (octopusPointer != null) {
        print("${octopusPointer!!} ")
        while (octopusPointer != null) {
            octopusPointer = octopusPointer.resolve(1, 0)
            print("${octopusPointer!!} ")
        }
        println()
        //octopusPointer = octopusPointer!!.resolve(matrix[0].size-1, 1)
    }

}
