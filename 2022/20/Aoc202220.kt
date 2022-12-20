import com.toto_castaldi.common.structure.CircularArray
import com.toto_castaldi.common.structure.PrevNextNode
import java.io.File
import java.util.*

class Aoc202220() {

    var index0: PrevNextNode<IdAndValue>? = null
    val circle = CircularArray<IdAndValue>()
    val ids = mutableListOf<String>()

    operator fun plus(value: Int) {
        val idAndValue = IdAndValue(value)
        circle.add(idAndValue)
        ids.add(idAndValue.id)
    }

    fun numberAfter0(pos: Int): Int? {
        return index0?.next(pos)?.data?.value
    }

    fun arrangeAllElements() {
        var count = 0
        val total = ids.size
        for (o in ids) {
            val element  = circle.findBy {
                it.id == o
            }!!
            circle.moveRigth(element, element.data.value)
            if (element.data.value == 0) {
                index0 = element
            }
            count ++
            println("${element} $count $total")
        }
        println("arranged")
    }

    class IdAndValue(val value: Int) {
        val id = UUID.randomUUID().toString()
        override fun toString(): String {
            return "$id $value"
        }
    }

    companion object {
        fun run(fileName: String) {
            val aoc = Aoc202220()
            File(fileName).forEachLine {line ->
                aoc + line.trim().toInt()
            }
            aoc.arrangeAllElements()
            println( aoc.numberAfter0(1000)!! + aoc.numberAfter0(2000)!! + aoc.numberAfter0(3000)!!)
        }
    }

}
