import com.toto_castaldi.common.algo.ManhattanDistance
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Line
import java.io.File
import java.lang.Math.max
import java.lang.Math.min

class Aoc202215() {

    private val sensorAreas = mutableSetOf<SeasonArea>()
    private val beacons = mutableSetOf<Coordinates>()

    fun countOccupiedSpotForBeacon(y: Int): Int {
        val intervals = mutableSetOf<IntRange>()
        for (area in sensorAreas) {
            var interval:IntRange ? = area.resolveInterval(y)
            if (interval != null) {
                val covering = intervals.filter {
                    it.contains(interval.last) || it.contains(interval.first) || intervals.contains(it)
                }
                if (covering.size > 1) throw Exception("a problem here")
                if (covering.size == 1) {
                    val other = covering[0]
                    println("covering $interval $other")
                    intervals.remove(other)
                    println("remove $other")
                    val i = min(interval.first, other.first) .. max(interval.last, other.last)
                    println("add $i")
                    intervals.add(i)
                } else {
                    println("add $interval")
                    intervals.add(interval)
                }

            }
        }
        var count = 0
        for (interval in intervals) {
            var delta = beacons.filter { it.y == y }.fold(0) {
                acc, value ->

                if (interval.contains(value.x)) acc + 1 else acc
            }
            count += interval.count() - delta
        }
        return count
    }

    operator fun plus(sensorBeaconDescription: String): Aoc202215 {
        val intBetween = {
            input: String, before: String, after: String ->

            if (after.isNotEmpty()) {
                input.trim().split(before)[1].trim().split(after)[0].toInt()
            } else {
                input.trim().split(before)[1].trim().toInt()
            }
        }
        //Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        val sX = intBetween(sensorBeaconDescription, "Sensor at x=", ",")
        val sY = intBetween(sensorBeaconDescription, "y=", ":")
        val bX = intBetween(sensorBeaconDescription, ": closest beacon is at x=", ",")
        val bY = intBetween(sensorBeaconDescription.trim().split(": closest beacon is at x=")[1], ", y=", "")

        val sensor = Coordinates(sX, sY)
        val beacon = Coordinates(bX, bY)

        beacons.add(beacon)

        var distance = ManhattanDistance.between(sensor,beacon)
        var a = Coordinates(sensor.x, sensor.y - distance)
        var b = Coordinates(sensor.x + distance, sensor.y)
        var c = Coordinates(sensor.x, sensor.y + distance)
        var d = Coordinates(sensor.x - distance, sensor.y)

        sensorAreas.add(SeasonArea(sensor, distance, a,b,c,d))

        return this
    }

    class SeasonArea(val sensor: Coordinates, val distance: Int, val a: Coordinates, val b: Coordinates, val c: Coordinates, val  d: Coordinates) {
        operator fun contains(coordinates: Coordinates): Boolean {
            val interval = resolveInterval(coordinates)
            return interval != null && interval.contains(coordinates.x)
        }

        fun resolveInterval(y: Int): IntRange? {
            return resolveInterval(Coordinates(sensor.x, y))
        }
        fun resolveInterval(coordinates: Coordinates): IntRange? {
            var interval: IntRange? = null
            //val (x, y) = coordinates
            val x = coordinates.x
            val y = coordinates.y
            if (y == a.y || y == c.y) {
                interval = x..x
            } else if (y == b.y) {
                interval = d.x..b.x
            } else if (y > a.y && y < d.y) {
                val f = Coordinates(Line(a, d).atY(y).toInt(), y)
                val s = Coordinates(Line(a, b).atY(y).toInt(), y)
                interval = f.x..s.x
            } else if (y > d.y && y < c.y) {
                val f = Coordinates(Line(d, c).atY(y).toInt(), y)
                val s = Coordinates(Line(b, c).atY(y).toInt(), y)
                interval = f.x..s.x
            }
            return interval
        }

    }
    fun isOccupied(coordinates: Coordinates) : Boolean{ 
        for (area in sensorAreas) {
            val o = coordinates in area
            if (o) return true
        }
        return false
    }

    companion object {
        fun run(fileName: String, y: Int): Int {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.countOccupiedSpotForBeacon(y)
        }
    }

}