import com.toto_castaldi.common.structure.CircularArray
import com.toto_castaldi.common.structure.PrevNextNode
import java.io.File
import java.util.*

class Aoc202220() {

    var index0: PrevNextNode<IdAndValue>? = null
    val circle = CircularArray<IdAndValue>()
    val ids = mutableListOf<String>()

    operator fun plus(value: Long) {
        val idAndValue = IdAndValue(value)
        circle.add(idAndValue)
        ids.add(idAndValue.id)
    }

    fun numberAfter0(pos: Int): Long? {
        return index0?.next(pos)?.data?.value
    }

    fun arrangeAllElements() {
        var count = 0
        for (o in ids) {
            val element  = circle.findBy {
                it.id == o
            }!!
            circle.moveRigth(element, element.data.value)
            if (element.data.value == 0L) {
                index0 = element
            }
            count ++
        }
        //println(circle.values(circle.findBy { it.value == 1 }!!))
    }

    fun decryptionKey(decryptionKey: Long) {
        for (c in circle.over()) {
            c.value *= decryptionKey
        }
    }

    class IdAndValue(var value: Long) {
        val id = UUID.randomUUID().toString()
        override fun toString(): String {
            return "$value"
        }
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc202220()
            File(fileName).forEachLine {line ->
                aoc + line.trim().toLong()
            }
            aoc.arrangeAllElements()
            println( aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)
        }
        fun run2(fileName: String) {
            val aoc = Aoc202220()
            File(fileName).forEachLine {line ->
                aoc + line.trim().toLong()
            }
            aoc.decryptionKey(811589153)
            for (i in 1 .. 10) {
                aoc.arrangeAllElements()
            }

            println( aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)
        }
    }

}
