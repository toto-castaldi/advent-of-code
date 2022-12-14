import com.toto_castaldi.common.Coordinates

class Aoc202214(val sandXStart: Int) {

    private var floorY: Int = -1
    private var maxY: Int = 0
    private var maxX: Int = 0
    private var minX: Int = Int.MAX_VALUE
    private var canFall = true
    val bricks = mutableSetOf<Coordinates>()

    operator fun plus(parsePath: List<Coordinates>): Aoc202214 {
        for (i in 0 until parsePath.size -1) {
            val pFrom = parsePath[i]
            val pTo = parsePath[i+1]

            val pathSequence = Coordinates.path(pFrom, pTo)
            bricks.add(pFrom)
            setPrintBounderies(pFrom)
            for (p in pathSequence) {
                bricks.add(p)
                setPrintBounderies(p)
            }
        }
        return this
    }

    private fun setPrintBounderies(c: Coordinates) {
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

    fun blockedSandCount(print : Boolean = false): Int {
        if (floorY == -1) {
            return blockedSandCountNoFloor(print)
        } else {
            return blockedSandCountWithFloor(print)
        }
    }

    private fun blockedSandCountWithFloor(print : Boolean = false, debug : Int = -1): Int {
        var time = 0
        var result = 0
        var currentSand: Coordinates? = null

        while (canFall && (debug == -1 || time < debug)) {
            time++

            if (currentSand == null) {
                currentSand = Coordinates(sandXStart, 0)
            }

            if (currentSand.clone().d() !in bricks && currentSand.clone().d().y < floorY) { //can move down
                currentSand = currentSand.clone().d()
            } else if (currentSand.clone().ld() !in bricks && currentSand.clone().ld().y < floorY) { //can move ld
                currentSand = currentSand.clone().ld()
            } else if (currentSand.clone().rd() !in bricks && currentSand.clone().d().y < floorY) { //can move rd
                currentSand = currentSand.clone().rd()
            }
            if ((
                        currentSand.clone().d() in bricks &&
                        currentSand.clone().ld() in bricks &&
                        currentSand.clone().rd() in bricks
                        ) || (
                        currentSand.clone().d().y == floorY
                        )) {
                bricks.add(currentSand.clone())
                result++
                currentSand = null
            }
            if (Coordinates(sandXStart, 0) in bricks) { //cave full
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

    private fun blockedSandCountNoFloor(print : Boolean = false, debug : Int = -1): Int {
        var time = 0
        var result = 0
        var currentSand: Coordinates? = null

        while (canFall && (debug == -1 || time < debug)) {
            time ++

            if (currentSand == null) {
                currentSand = Coordinates(sandXStart, 0)
            }

            if (currentSand.clone().d() !in bricks) { //can move down
                currentSand = currentSand.clone().d()
            } else if (currentSand.clone().ld() !in bricks) { //can move ld
                currentSand = currentSand.clone().ld()
            } else if (currentSand.clone().rd() !in bricks) { //can move rd
                currentSand = currentSand.clone().rd()
            }
            if (
                currentSand.clone().d() in bricks &&
                currentSand.clone().ld() in bricks &&
                currentSand.clone().rd() in bricks
            ) {
                bricks.add(currentSand.clone())
                result ++
                currentSand = null
            } else if (currentSand.y >= maxY) {
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

    private fun printCave(currentSand: Coordinates?) {
        for (y in 0..maxY) {
            for (x in minX..maxX) {
                val c = Coordinates(x, y)
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
        val parsePath = { pathStringDef: String -> pathStringDef.trim().split(" -> ").map { Coordinates(it.trim().split(",")[0].toInt(), it.trim().split(",")[1].toInt())}}
    }

}


