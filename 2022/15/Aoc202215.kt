import com.toto_castaldi.common.Numbers
import com.toto_castaldi.common.algo.ManhattanDistance
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Diamond
import com.toto_castaldi.common.structure.Line
import java.io.File
import java.lang.Math.max
import java.lang.Math.min

class Aoc202215() {

    private val sensorAreas = mutableSetOf<Diamond>()
    private val beacons = mutableSetOf<Coordinates>()

    private fun freeSpotAt(y: Int): Pair<Int, Int>? {
        var intervals = setOf<IntRange>()
        for (area in sensorAreas) {
            var interval:IntRange ? = area.resolveInterval(y)
            if (interval != null) {
                intervals = Numbers.merged(intervals, interval)
            }
        }
        if (intervals.size == 1) {
            return Pair(intervals.first()!!.first -1, intervals.first()!!.last + 1)
        }
        return null
    }
    fun countOccupiedSpotForBeacon(y: Int): Int {
        var intervals = setOf<IntRange>()
        for (area in sensorAreas) {
            var interval:IntRange ? = area.resolveInterval(y)
            if (interval != null) {
                intervals = Numbers.merged(intervals, interval)
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

        sensorAreas.add(Diamond(sensor, distance))

        return this
    }

    fun isOccupied(coordinates: Coordinates) : Boolean{ 
        for (area in sensorAreas) {
            val o = coordinates in area
            if (o) return true
        }
        return false
    }

    fun distressBeaconTuningFrequency(max: Int): Int {
        for (y in 0 .. max) {
            println(y)
            if (y == 0) {
                println("ddd")
            }
            var freeSpot = freeSpotAt(y)
            if (freeSpot != null) {
                println(freeSpot)
                if (freeSpot.first in 0..max && Coordinates(freeSpot.first, y) !in beacons) {
                    return freeSpot.first * 4000000 + y
                } else if (freeSpot.second in 0..max && Coordinates(freeSpot.second, y) !in beacons) {
                    return freeSpot.second * 4000000 + y
                }
            }
        }
        throw Exception("... uhm")
    }



    companion object {
        fun run(fileName: String, y: Int): Int {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.countOccupiedSpotForBeacon(y)
        }
    }

}