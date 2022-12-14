import com.toto_castaldi.common.Coordinates

class Aoc202214(val sandXStart: Int) {

    private var maxY: Int = 0
    private var maxX: Int = 0
    private var minX: Int = Int.MAX_VALUE
    private var currentSand: Coordinates? = null
    private var fallenSand = true
    val bricks = mutableSetOf<Coordinates>()

    operator fun plus(parsePath: List<Coordinates>): Aoc202214 {
        for (i in 0 until parsePath.size -1) {
            val pFrom = parsePath[i]
            val pTo = parsePath[i+1]

            val pathSequence = Coordinates.path(pFrom, pTo)
            bricks.add(pFrom)
            if (pFrom.y > maxY) {
                maxY = pFrom.y
            }
            if (pFrom.x < minX) {
                minX = pFrom.x
            }
            if (pFrom.x > maxX) {
                maxX = pFrom.x
            }
            for (p in pathSequence) {
                bricks.add(p)
                if (p.y > maxY) {
                    maxY = p.y
                }
                if (p.x < minX) {
                    minX = p.x
                }
                if (p.x > maxX) {
                    maxX = p.x
                }
            }
        }
        return this
    }

    fun blockedSandCount(print : Boolean = false): Int {
        var time = 0
        var debug = -1
        var result = 0

        while (fallenSand && (debug == -1 || time < debug)) {
            time ++

            if (currentSand == null) {
                currentSand = Coordinates(sandXStart, 0)
            }

            if (currentSand!!.clone().d() !in bricks) { //can move down
                currentSand = currentSand!!.clone().d()
            } else if (currentSand!!.clone().ld() !in bricks) { //can move ld
                currentSand = currentSand!!.clone().ld()
            } else if (currentSand!!.clone().rd() !in bricks) { //can move ld
                currentSand = currentSand!!.clone().rd()
            }
            if (
                currentSand!!.clone().d() in bricks &&
                currentSand!!.clone().ld() in bricks &&
                currentSand!!.clone().rd() in bricks
            ) {//test stopped
                bricks.add(currentSand!!.clone())
                result ++
                currentSand = null
            } else if (currentSand!!.y >= maxY) {
                fallenSand = false
                currentSand = null
            }

            if (print) {
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
                println("round ${time} ${result}")
            }


        }


        return result
    }

    companion object {
        //"498,4 -> 498,6 -> 496,6"
        val parsePath = { pathStringDef: String -> pathStringDef.trim().split(" -> ").map { Coordinates(it.trim().split(",")[0].toInt(), it.trim().split(",")[1].toInt())}}
    }

}
