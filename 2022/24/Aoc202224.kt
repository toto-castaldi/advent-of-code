
import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File

class Aoc202224() {

    private val winds = mutableSetOf<IntCoordinates>()
    private var walls = mutableSetOf<IntCoordinates>()
    private var terrain = mutableSetOf<IntCoordinates>()
    private lateinit var start:IntCoordinates
    private lateinit var end:IntCoordinates

    fun map(y: Int, height: Int, elvesPos: String) {
        for ((x, c) in elvesPos.toCharArray().toList().withIndex()) {
            val p = IntCoordinates(x,y)
            when (c) {
                '#' -> walls.add(p)
                '.' -> if (y == 0) start = p else if (y + 1 == height) end = p else terrain.add(p)
                '^' -> winds.add(Wind(CrossDirection.U, IntCoordinates(x,y)))
            }

        }
    }

    data class Wind(val crossDirection: CrossDirection,val coordinates: IntCoordinates)

    fun stepToExit(): Int {
        TODO("Not yet implemented")
    }

    companion object {

        fun run1(fileName: String) {
            val aoc = Aoc202224()
            var y = 0
            val height = File(fileName).readLines().size
            File(fileName).forEachLine {
                aoc.map(y++, height, it)
            }
            println( aoc.stepToExit())
        }

    }
}
