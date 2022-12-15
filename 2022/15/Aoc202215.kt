import com.toto_castaldi.common.algo.ManhattanDistance
import com.toto_castaldi.common.structure.Coordinates
import java.io.File

class Aoc202215() {

    private val sensorVision = mutableSetOf<Coordinates>()
    private val beacons = mutableSetOf<Coordinates>()

    fun countOccupiedSpotForBeacon(yPos: Int): Int {
        return sensorVision.filter { it.y == yPos }.size - beacons.filter { it.y == yPos }.size
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

        val s = Coordinates(sX, sY)
        val b = Coordinates(bX, bY)

        beacons.add(b)

        var d: Int = ManhattanDistance.between(s,b)

        for (y in s.y -d .. s.y + d) {
            val w = if (y<= s.y) d - s.y + y else d + s.y - y
            for (x in s.x - w .. s.x + w) {
                sensorVision.add(Coordinates(x,y))
            }
        }

        println("added $sensorBeaconDescription")
        return this
    }

    val isOccupied = { coordinates: Coordinates -> coordinates in sensorVision }

    companion object {
        fun run(fileName: String, y: Int): Int {
            val aoc = Aoc202215()
            File(fileName).readLines().forEach { aoc + it }
            return aoc.countOccupiedSpotForBeacon(y)
        }
    }

}