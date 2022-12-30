import com.toto_castaldi.common.structure.IntCoordinates
import kotlin.math.sign

private fun IntCoordinates.d(): IntCoordinates {
    return move(0,1)
}

private fun IntCoordinates.ld(): IntCoordinates {
    return move(-1,1)
}

private fun IntCoordinates.rd(): IntCoordinates {
    return move(1,1)
}

class Aoc202214(val sandXStart: Int) {

    private var floorY: Int = -1
    private var maxY: Int = 0
    private var maxX: Int = 0
    private var minX: Int = Int.MAX_VALUE
    val bricks = mutableSetOf<IntCoordinates>()

    operator fun plus(parsePath: List<IntCoordinates>): Aoc202214 {
        for (i in 0 until parsePath.size -1) {
            val pFrom = parsePath[i]
            val pTo = parsePath[i+1]

            val pathSequence = path2D(pFrom, pTo)
            bricks.add(pFrom)
            setPrintBounderies(pFrom)
            for (p in pathSequence) {
                bricks.add(p)
                setPrintBounderies(p)
            }
        }
        return this
    }

    fun path2D(source: IntCoordinates, destination: IntCoordinates) = sequence<IntCoordinates> {
        var step = source

        while (step != destination) {
            val direction = destination - step
            if (direction.x != 0) {
                step = step.clone().move(sign(direction.x.toDouble()).toInt(),0)
            } else {
                step = step.clone().move(0, sign(direction.y.toDouble()).toInt())
            }
            yield(step)
        }
    }

    private fun setPrintBounderies(c: IntCoordinates) {
        if (c.y > maxY) {
            maxY = c.y
        }
        if (c.x < minX) {
            minX = c.x
        }
        if (c.x > maxX) {
            maxX = c.x
        }
    }

    fun blockedSandCount(print : Boolean = false, debug : Int = -1): Int {
        var time = 0
        var result = 0
        var currentSand: IntCoordinates? = null
        var canFall = true

        while (canFall && (debug == -1 || time < debug)) {
            time ++

            if (currentSand == null) {
                currentSand = IntCoordinates(sandXStart, 0)
            }

            if (currentSand.clone().d() !in bricks && (floorY == -1 || currentSand.clone().d().y < floorY)) { //can move down
                currentSand = currentSand.clone().d()
            } else if (currentSand.clone().ld() !in bricks && (floorY == -1 || currentSand.clone().ld().y < floorY)) { //can move ld
                currentSand = currentSand.clone().ld()
            } else if (currentSand.clone().rd() !in bricks && (floorY == -1 || currentSand.clone().rd().y < floorY)) { //can move rd
                currentSand = currentSand.clone().rd()
            }
            if ((
                        currentSand.clone().d() in bricks &&
                        currentSand.clone().ld() in bricks &&
                        currentSand.clone().rd() in bricks
                        ) || (
                        currentSand.clone().d().y == floorY && floorY != -1
                        )) {
                bricks.add(currentSand.clone())
                result ++
                currentSand = null
            }
            if (currentSand != null &&
                (currentSand.y >= maxY && floorY == -1) ||
                (IntCoordinates(sandXStart, 0) in bricks) //cave full
            ){
                canFall = false
                currentSand = null
            }


            if (print) {
                printCave(currentSand)
                println("round ${time} ${result}")
            }
        }
        return result
    }

    fun addFloor(delta: Int) {
        floorY = maxY + delta
    }

    private fun printCave(currentSand: IntCoordinates?) {
        for (y in 0..maxY) {
            for (x in minX..maxX) {
                val c = IntCoordinates(x, y)
                if (c in bricks) {
                    print("#")
                } else if (c == currentSand) {
                    print("+")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    companion object {
        fun parsePaths(aoc : Aoc202214 , lines: List<String>): Aoc202214 {
            for (line in lines) {
                aoc + parsePath(line)
            }
            return aoc
        }

        //"498,4 -> 498,6 -> 496,6"
        val parsePath = { pathStringDef: String -> pathStringDef.trim().split(" -> ").map { IntCoordinates(it.trim().split(",")[0].toInt(), it.trim().split(",")[1].toInt()) }}
    }

}