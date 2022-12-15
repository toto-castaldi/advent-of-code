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
        val intervals = matchingY(y)
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
        val intervals = matchingY(y)
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

    private fun matchingY(
        y: Int
    ): Set<IntRange> {
        var result = setOf<IntRange>()
        sensorAreas.forEach { area ->
            result = area.resolveInterval(y)?.let { Numbers.merged(result, it) } ?: result
        }
        return result
    }

    operator fun plus(sensorBeaconDescription: String): Aoc202215 {
        //Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        val match = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex().find(sensorBeaconDescription)!!
        val sX = match.groups[1]!!.value.toInt()
        val sY = match.groups[2]!!.value.toInt()
        val bX = match.groups[3]!!.value.toInt()
        val bY = match.groups[4]!!.value.toInt()

        val sensor = Coordinates(sX, sY)
        val beacon = Coordinates(bX, bY)

        beacons.add(beacon)

        sensorAreas.add(Diamond(sensor, ManhattanDistance.between(sensor,beacon)))

        return this
    }

    fun distressBeaconTuningFrequency(max: Int): Long {
        (0..max).forEach{
            val freeSpot = freeSpotAt(it)
            if (freeSpot.isNotEmpty()) {
                val suitableX = freeSpot.find { it in 0..max }
                if (suitableX != null) {
                    return suitableX.toLong() * 4000000L + it.toLong()
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
        fun run2(fileName: String, max: Int): Long {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.distressBeaconTuningFrequency(max)
        }
    }

}