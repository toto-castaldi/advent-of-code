import com.toto_castaldi.common.structure.CircularArray

class Aoc202220() {

    var index0: Int = -1
    val values = CircularArray<Int>()

    operator fun plus(value: Int) {
        values.add(value)
    }

    fun nmberAfter0(pos: Int): Int {
        return values[index0 + pos]
    }

    fun arrangeAllElements() {
        for (i in 0 until values.size) {
            val newIndex = values.moveRight(0, values[0])
            if (values[newIndex] == 0) {
                index0 = newIndex
            }
        }
    }

    companion object {
        fun run(fileName: String) {
            println( fileName)
        }
    }

}
