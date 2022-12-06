import java.io.File
import kotlin.math.abs

private class Octopus(var energy: Int) {

    private var neighbors: MutableMap<Coordinates, Octopus> = mutableMapOf<Coordinates, Octopus>()

    fun neighbor(
        x: Int,
        y: Int,
        neighbor: Octopus
    ) {
        neighbors[Coordinates(x, y)] = neighbor
        neighbor.neighbors[Coordinates(-x, -y)] = this
    }

    fun resolve(
        stepX: Int,
        stepY: Int
    ): Octopus? {
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
        return energy.toString()
    }

    fun incEnergy() {
        if (energy <= 9) {
            energy++
            if (energy > 9) {
                for (n in neighbors.values) {
                    n.incEnergy()
                }
            }
        }
    }

    fun flash(): Boolean {
        if (energy > 9) {
            energy = 0
            return true
        }
        return false
    }

}

private fun printOctopuses(
    octopus: Octopus
) {
    navigate(octopus, { print("$it") }, { println() })

}

private data class Coordinates(val x: Int, val y: Int)

private fun step(
    octopus: Octopus
): Int {
    var flashes = 0

    navigate(octopus, { it.incEnergy() }, {})
    navigate(octopus, { flashes += if (it.flash()) 1 else 0 }, {})

    return flashes
}

private fun navigate(
    octopus: Octopus,
    action: (it: Octopus) -> Unit,
    changeLine: () -> Unit
) {
    var navigation: Octopus? = octopus
    var width = 0
    while (navigation != null) {
        action(navigation)
        if (navigation.resolve(1, 0) != null) {
            width ++
            navigation = navigation.resolve(1, 0)!!
        } else {
            changeLine()
            navigation = navigation.resolve(-width, 1)
            width = 0
        }
    }
}

fun main(
    args: Array<String>
) {
    var octopusPointer: Octopus? = null
    var count = 0
    val matrix = File(args[0]).readLines().map { line -> line.toCharArray().map { c -> c.digitToInt() }.toMutableList() }
    for (y in 0 until matrix.size) {
        for (x in 0 until matrix[y].size) {
            count ++
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

    printOctopuses(octopusPointer!!)

    var allFlashes = 0
    var flashesTogether = 0
    var i = 0

    while (flashesTogether == 0) {
        i++
        val stepFlashes = step(octopusPointer)
        if (i < 100) {
            allFlashes += stepFlashes
        }

        printOctopuses(octopusPointer)

        if (stepFlashes == count) {
            flashesTogether = i
        }
    }
    println(allFlashes)
    println(flashesTogether)
}