import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun main(
    args: Array<String>
) {
    println(part(args[0], 2))
    println(part(args[0], 10))
}

private fun part(fileName : String , len : Int) : Int{
    val tailPassing = mutableSetOf<IntCoordinates>()

    val rope = (1..len).map { IntCoordinates(0, 0) }.toMutableList()

    File(fileName).forEachLine { line ->

        val (dir, stepsString) = line.split(" ")
        val steps = stepsString.toInt()

        for (i in 0 until steps) {
            val headCoordinates = rope.first()
            when (dir) {
                "R" -> headCoordinates.move(1, 0)
                "U" -> headCoordinates.move(0, -1)
                "L" -> headCoordinates.move(-1, 0)
                "D" -> headCoordinates.move(0, 1)
            }
            for (j in 0 until rope.size - 1) {
                val first = rope[j]
                val second = rope[j + 1]
                val distance = first - second
                val x = if (abs(distance.y) > 1 || abs(distance.x) > 1) sign(distance.x.toDouble()).toInt() else 0
                val y = if (abs(distance.y) > 1 || abs(distance.x) > 1) sign(distance.y.toDouble()).toInt() else 0

                if (j == rope.size - 2) {
                    tailPassing.add(rope.last().clone())
                }
                second.move(x, y)
            }
        }
    }
    tailPassing.add(rope.last().clone())

    return tailPassing.size
}
