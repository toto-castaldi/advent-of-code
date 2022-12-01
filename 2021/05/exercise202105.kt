import java.io.File
import java.util.Arrays
import java.util.Collections

class Line  {

    val points : ArrayList<Point> = ArrayList()
    val description : String
    val isDiagonal : Boolean
    
    constructor (descriptionC : String) {
        val (start, end) = descriptionC.split(" -> ")
        val pointA = Point (start)
        val pointB = Point (end)
        description = descriptionC
        
        val normalize  = { num : Int -> 
            when {
                num > 0 -> 1
                num < 0 -> -1
                else -> 0
            }
        }

        val vector : Point = Point(normalize(pointB.x - pointA.x), normalize(pointB.y - pointA.y))

        if (vector.x == 0 || vector.y == 0) {
            isDiagonal = false;
        } else {
            isDiagonal = true;
        }

        var p = pointA
        while (p != pointB) {
            points.add(p)
            p = p.add(vector)
        }
        points.add(p)
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

    fun add(p : Point) : Point {
        return Point(x + p.x, y + p.y)
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
    
    fun draw(line : Line) {
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
    val lines : ArrayList<Line> = ArrayList(inputLines.map { Line(it) })
    val plane1 : Plane = Plane()
    val plane2 : Plane = Plane()
    for (line in lines) {
        print("$line ")
        println(line.points)
        if (!line.isDiagonal) plane1.draw(line)
        plane2.draw(line)
    }

    val result1: Int = plane1.countPointsWithMinOverlapping(2)

    println("result1 $result1")

    val result2: Int = plane2.countPointsWithMinOverlapping(2)

    println("result2 $result2")
}