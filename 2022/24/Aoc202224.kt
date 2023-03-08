
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File

class Aoc202224() {

    private var winds = mutableSetOf<IntCoordinates>()

    fun map(y: Int, elvesPos: String) {
        for ((x, c) in elvesPos.toCharArray().toList().withIndex()) {
            if (c == '#') {
                winds.add(IntCoordinates(x, y))
            }
        }
    }

    fun stepToExit(): Int {
        TODO("Not yet implemented")
    }

    companion object {

        fun run1(fileName: String) {
            val aoc = Aoc202224()
            var y = 0
            File(fileName).forEachLine {
                aoc.map(y++, it)
            }
            println( aoc.stepToExit())
        }

    }
}
