import com.toto_castaldi.common.structure.CircularArray
import java.util.*

class Aoc202220() {

    var index0: Int = -1
    val circle = CircularArray<IdAndValue>()
    val ids = mutableListOf<String>()

    operator fun plus(value: Int) {
        val idAndValue = IdAndValue(value)
        circle.add(idAndValue)
        ids.add(idAndValue.id)
    }

    fun nmberAfter0(pos: Int): Int {
        return circle[index0 + pos].value
    }

    fun arrangeAllElements() {
        for (o in ids) {
            val element  = circle.findBy {
                it.id == o
            }!!
            val newIndex = circle.moveRigth(element, element.data.value)
            if (element.data.value == 0) {
                index0 = newIndex
            }
        }
    }

    class IdAndValue(val value: Int) {
        val id = UUID.randomUUID().toString()
    }

    companion object {
        fun run(fileName: String) {
            println( fileName)
        }
    }

}
