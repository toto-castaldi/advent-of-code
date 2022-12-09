import com.toto_castaldi.common.Coordinates
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
    val tailPassing = mutableSetOf<Coordinates>()

    tailPassing.add(Coordinates(0, 0))

    val rope = (1..len).map { Coordinates(0, 0) }.toMutableList()

    File(fileName).forEachLine { line ->

        val (dir, stepsString) = line.split(" ")
        val steps = stepsString.toInt()

        for (i in 0 until steps) {
            //move head
            val headCoordinates = rope.first()
            when (dir) {
                "R" -> headCoordinates.move(1, 0)
                "U" -> headCoordinates.move(0, -1)
                "L" -> headCoordinates.move(-1, 0)
                "D" -> headCoordinates.move(0, 1)
            }
            var lastTailPos = headCoordinates
            for (j in 0 until rope.size - 1) {
                val first = rope[j]
                val second = rope[j + 1]
                val distance = first - second
                val x  = if (abs(distance.y) > 1 || abs(distance.x) > 1) sign(distance.x.toDouble()).toInt() else 0
                val y  = if (abs(distance.y) > 1 || abs(distance.x) > 1) sign(distance.y.toDouble()).toInt() else 0

                lastTailPos = second.clone()
                second.move(x, y)
            }

            tailPassing.add(lastTailPos)
        }
    }
    tailPassing.add(rope.last())

    return tailPassing.size
}
