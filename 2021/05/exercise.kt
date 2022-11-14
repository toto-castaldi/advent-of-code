import java.io.File
import java.util.Arrays
import java.util.Collections

class LineHV  {

    val points : ArrayList<Point> = ArrayList()
    val description : String
    
    constructor (descriptionC : String) {
        var startPoint : Point? = null
        var endPoint : Point? = null
        var hVsV : Boolean? = null
        val (start, end) = descriptionC.split(" -> ")
        val pointA = Point (start)
        val pointB = Point (end)
        description = descriptionC
        if (pointA.x == pointB.x && pointA.y != pointB.y) { 
            hVsV = false
            if (pointA.y < pointB.y) {
                startPoint = pointA
                endPoint = pointB
            } else {
                startPoint = pointB
                endPoint = pointA
            }
        } else if (pointA.y == pointB.y && pointA.x != pointB.x) { 
            hVsV = true
            if (pointA.x < pointB.x) {
                startPoint = pointA
                endPoint = pointB
            } else {
                startPoint = pointB
                endPoint = pointA
            }
        }
        if (startPoint != null && endPoint != null) {
            if (hVsV == true) {
                for (x in startPoint.x..endPoint.x) {
                    points.add(Point(x, startPoint.y))
                }
            } else {
                for (y in startPoint.y..endPoint.y) {
                    points.add(Point(startPoint.x, y))
                }
            }
        }
    }

    override fun toString() : String {
        return description
    }


}

class Point {

    val x : Int
    val y : Int

    constructor (description : String) {
        val (xS, yS) = description.split(",")
        this.x = xS.trim().toInt()
        this.y = yS.trim().toInt()
    }

    constructor (xC : Int, yC : Int) {
        x = xC
        y = yC
    }

    override fun toString() : String {
        return "($x,$y)"
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Point

        if (x == other.x && y == other.y) return true

        return false
    }
}

class Plane () {

    val points : HashMap<Point, Int> = HashMap()

    
    fun draw(line : LineHV) {
        print("$line ")
        println(line.points)
        for (point in line.points) {
            points.put(point, points.getOrDefault(point, 0) + 1)
        }
    }

    fun countPointsWithMinOverlapping(min : Int) : Int {
        return points.values.fold(0, { acc, value -> acc + (if (value >= min) 1 else 0 )})
    }
}

fun main() {
    val inputLines: List<String> = File("input.txt").readLines()
    val lines : ArrayList<LineHV> = ArrayList(inputLines.map { LineHV(it) })
    val plane : Plane = Plane()
    for (line in lines) {
        plane.draw(line)
    }

    println(plane.points)

    val result: Int = plane.countPointsWithMinOverlapping(2)

    println("result $result")



}