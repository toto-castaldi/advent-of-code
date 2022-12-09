import com.toto_castaldi.common.Coordinates
import java.io.File
import kotlin.math.abs
import kotlin.math.sign

fun main(
    args: Array<String>
) {
    part(args[0], 2)
    part(args[0], 12)

}

private fun part(fileName : String , len : Int) {
    val tailPassing = mutableMapOf<Coordinates, Int>()
    tailPassing[Coordinates(0, 0)] = 1

    val rope = (1..len).map { Coordinates(0, 0) }.toMutableList()

    File(fileName).forEachLine { line ->

        val (dir, stepsString) = line.split(" ")
        val steps = stepsString.toInt()

        for (i in 0 until steps) {
            //move head
            val headCoordinates = rope[0]
            when (dir) {
                "R" -> headCoordinates.move(1, 0)
                "U" -> headCoordinates.move(0, -1)
                "L" -> headCoordinates.move(-1, 0)
                "D" -> headCoordinates.move(0, 1)
            }
            var lastTailPos = rope[0]
            for (j in 0 until rope.size - 1) {
                val first = rope[j]
                val second = rope[j + 1]
                val distance = first - second

                var x  = 0
                var y  = 0

                if (distance.x != 0 || distance.y != 0) {
                    if (distance.y == 0 && abs(distance.x) > 1) x = sign(distance.x.toDouble()).toInt()
                    else if (distance.x == 0 && abs(distance.y) > 1) y = sign(distance.y.toDouble()).toInt()
                    else if (abs(distance.y) > 1 || abs(distance.x) > 1) {
                        x = sign(distance.x.toDouble()).toInt()
                        y = sign(distance.y.toDouble()).toInt()
                    }
                }
                lastTailPos = second.clone()
                second.move(x, y )
            }

            tailPassing[lastTailPos] = tailPassing[lastTailPos] ?.let { it + 1 } ?: 1

        }
    }
    val tail = rope[rope.size - 1]
    tailPassing[tail] = if (tail in tailPassing.keys) tailPassing[tail]!! + 1 else 1

    println(tailPassing.keys.size)
}
