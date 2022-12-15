import com.toto_castaldi.common.Numbers
import com.toto_castaldi.common.algo.ManhattanDistance
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Diamond
import java.io.File

class Aoc202215() {

    private val sensorAreas = mutableSetOf<Diamond>()
    private val beacons = mutableSetOf<Coordinates>()

    private fun freeSpotAt(y: Int): Set<Int> {
        val result = mutableSetOf<Int>()
        var intervals = setOf<IntRange>()
        for (area in sensorAreas) {
            val interval:IntRange ? = area.resolveInterval(y)
            if (interval != null) {
                intervals = Numbers.merged(intervals, interval)
            }
        }
        if (intervals.isNotEmpty()) {
            val min = intervals.minBy { it.first }
            val max = intervals.maxBy { it.last }
            val empties = Numbers.freeSpots(intervals)

            result.add(min.first - 1)
            result.add(max.last + 1)
            result.addAll(empties)
        }
        return result
    }
    fun countOccupiedSpotForBeacon(y: Int): Int {
        var intervals = setOf<IntRange>()
        for (area in sensorAreas) {
            val interval:IntRange ? = area.resolveInterval(y)
            if (interval != null) {
                intervals = Numbers.merged(intervals, interval)
            }
        }
        var count = 0
        for (interval in intervals) {
            val delta = beacons.filter { it.y == y }.fold(0) {
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

        sensorAreas.add(Diamond(sensor, ManhattanDistance.between(sensor,beacon)))

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
            val freeSpot = freeSpotAt(y)
            if (freeSpot.isNotEmpty()) {
                val suitableX = freeSpot.find { it in 0..max }
                if (suitableX != null) {
                    return suitableX * 4000000 + y
                }
            }
        }
        throw Exception("... uhm")
    }



    companion object {
        fun run1(fileName: String, y: Int): Int {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.countOccupiedSpotForBeacon(y)
        }
        fun run2(fileName: String, max: Int): Int {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.distressBeaconTuningFrequency(max)
        }
    }

}