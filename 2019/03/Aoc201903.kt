import com.toto_castaldi.common.algo.ManhattanDistance
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File

class Aoc201903() {

    private val points = mutableMapOf<IntCoordinates, Int>()
    
    operator fun plus(s: String) {
        //R8,U5,L5,D3
        val directions = s.split(",").map {
            it.trim()
        }

        val startingPoint = IntCoordinates(0,0)
        for (dir in directions) {
            val step = IntCoordinates(0,0)
            when (dir[0]) {
                'R' -> step.move(1,0)
                'L' -> step.move(-1,0)
                'U' -> step.move(0,-1)
                'D' -> step.move(0,1)
            }
            val times = dir.substring(1).toInt()
            for (i in 1 .. times) {
                val p = startingPoint.move(step.x, step.y).clone()
                points[p] = points[p]?.plus(1) ?: 1
            }
        }

    }

    fun minimalManhattanDistanceOfIntersections(): Int {
        var minMd = 0
        for ((p, count) in points.entries) {
            if (count > 1) {
                val md = ManhattanDistance.between(IntCoordinates(0, 0), p)
                if (md < minMd || minMd == 0) {
                    minMd = md
                }
            }
        }
        return minMd
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201903()
            for (line in File(fileName).readLines()) {
                aoc + line
            }
            println(aoc.minimalManhattanDistanceOfIntersections())
        }

    }

}
