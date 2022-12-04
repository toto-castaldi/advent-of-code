import java.io.File
import kotlin.math.abs

private class Octopus(val energy: Int) {

    private var neighbors: MutableMap<Coordinates, Octopus>
    private var currentyEnergy: Int

    init {
        currentyEnergy = energy
        neighbors = mutableMapOf<Coordinates, Octopus>()
    }

    fun neighbor(x: Int, y: Int, neighbor: Octopus) {
        neighbors[Coordinates(x, y)] = neighbor
        neighbor.neighbors[Coordinates(-x, -y)] = this
    }

    fun resolve(stepX: Int, stepY: Int): Octopus? {
        return if (stepX == 0 && stepY == 0) {
            this
        } else if (abs(stepX) <= 1 && abs(stepY) <= 1) {
            neighbors[Coordinates(stepX, stepY)]
        } else {
            if (stepX < 0 && neighbors.containsKey(Coordinates(-1, 0))) {
                neighbors[Coordinates(-1, 0)]!!.resolve(stepX +1, stepY)
            } else if (stepX > 0 && neighbors.containsKey(Coordinates(1, 0))) {
                neighbors[Coordinates(1, 0)]!!.resolve(stepX -1, stepY)
            } else if (stepY < 0 && neighbors.containsKey(Coordinates(0, -1))) {
                neighbors[Coordinates(0, -1)]!!.resolve(stepX, stepY+1)
            } else {
                neighbors[Coordinates(0, 1)]!!.resolve(stepX, stepY-1)
            }
        }
    }

    override fun toString(): String {
        return currentyEnergy.toString()
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
                    octopus.neighbor(0, -1, octopusPointer.resolve(-matrix[y].size+1, 0)!!)
                }
            } else {
                octopus.neighbor(-1, 0, octopusPointer!!)
                if (y > 0) {
                    val upLeft = octopusPointer.resolve(0, -1)!!
                    val up = upLeft.resolve(1, 0)!!
                    octopus.neighbor(-1, -1, upLeft)
                    octopus.neighbor(0, -1, up)
                    up.neighbor(-1, 1, octopusPointer)
                }
            }
            octopusPointer = octopus
        }
    }
    octopusPointer = octopusPointer!!.resolve(-matrix[0].size+1, -matrix.size+1 )

    while (octopusPointer != null) {
        print("${octopusPointer}")
        if (octopusPointer.resolve(1, 0) == null) {
            println()
            octopusPointer = octopusPointer.resolve(-matrix[0].size+1, 1)
        } else {
            octopusPointer = octopusPointer.resolve(1, 0)
        }
    }

}
