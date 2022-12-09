import com.toto_castaldi.common.Coordinates
import java.io.File
import kotlin.math.abs

fun main(
    args: Array<String>
) {
    test()

    var tailPassing = mutableMapOf<Coordinates, Int>()
    tailPassing[Coordinates(0,0)] = 1

    var headCoordinates = Coordinates(0, 0)
    var tailCoordinates = Coordinates(0, 0)

    println("$headCoordinates $tailCoordinates")
    File(args[0]).forEachLine {
        line ->

        val (dir, stepsString) = line.split(" ")
        val steps = stepsString.toInt()

        for (i in 0 until steps) {
            //move head
            when (dir) {
                "R" -> headCoordinates = Coordinates(headCoordinates.x + 1, headCoordinates.y)
                "U" -> headCoordinates = Coordinates(headCoordinates.x , headCoordinates.y -1)
                "L" -> headCoordinates = Coordinates(headCoordinates.x - 1, headCoordinates.y)
                "D" -> headCoordinates = Coordinates(headCoordinates.x, headCoordinates.y + 1)
            }
            //move tail
            val d = distance(headCoordinates, tailCoordinates)

            if (d.x != 0 || d.y != 0) {
                if (d.y == 0) { //horizontal
                    if (abs(d.x) > 1) {
                        tailCoordinates = moveTail(tailPassing, tailCoordinates, if (d.x > 0) 1 else -1, 0)
                    }
                } else if (d.x == 0) { //vertical
                    if (abs(d.y) > 1) {
                        tailCoordinates = moveTail(tailPassing, tailCoordinates, 0, if (d.y > 0) 1 else -1)
                    }
                } else { //diagonal
                    if (abs(d.y) > 1 || abs(d.x) > 1) {
                        tailCoordinates = moveTail(tailPassing, tailCoordinates, if (d.x > 0) 1 else -1, if (d.y > 0) 1 else -1)
                    }
                }
            }
        }
    }
    tailPassing[tailCoordinates] = if (tailCoordinates in tailPassing.keys) tailPassing[tailCoordinates]!! + 1 else 1

    println(tailPassing.keys.size)

}

fun moveTail(
    tailPassing: MutableMap<Coordinates, Int>,
    tailCoordinates: Coordinates,
    x: Int,
    y: Int): Coordinates {
    tailPassing[tailCoordinates] = if (tailCoordinates in tailPassing.keys) tailPassing[tailCoordinates]!! + 1 else 1
    return Coordinates(tailCoordinates.x + x, tailCoordinates.y + y)
}

fun distance(toCoord: Coordinates, fromCoord: Coordinates): Coordinates {
    return Coordinates(toCoord.x - fromCoord.x, toCoord.y - fromCoord.y)
}

private fun test() {
    assert(42 == 42)
}