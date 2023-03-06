import com.toto_castaldi.common.WindRose
import com.toto_castaldi.common.structure.IntCoordinates

class Aoc202223() {

    private var elves = mutableSetOf<IntCoordinates>()
    private val rules = listOf(
        {
            elve: IntCoordinates, elves: Set<IntCoordinates> ->

            if (WindRose.n(elve) !in elves && WindRose.ne(elve) !in elves && WindRose.nw (elve) !in elves) {
                WindRose.n(elve)
            } else {
                null
            }
        },
        {
                elve: IntCoordinates, elves: Set<IntCoordinates> ->

                if (WindRose.s(elve) !in elves && WindRose.se(elve) !in elves && WindRose.sw (elve) !in elves) {
                    WindRose.s(elve)
                } else {
                    null
                }
        },
        {
                elve: IntCoordinates, elves: Set<IntCoordinates> ->

            if (WindRose.w(elve) !in elves && WindRose.nw(elve) !in elves && WindRose.sw (elve) !in elves) {
                WindRose.w(elve)
            } else {
                null
            }
        },
        {
                elve: IntCoordinates, elves: Set<IntCoordinates> ->

            if (WindRose.e(elve) !in elves && WindRose.ne(elve) !in elves && WindRose.se (elve) !in elves) {
                WindRose.e(elve)
            } else {
                null
            }
        }
    )

    fun map(y: Int, elvesPos: String) {
        for ((x, c) in elvesPos.toCharArray().toList().withIndex()) {
            if (c == '#') {
                elves.add(IntCoordinates(x, y))
            }
        }
    }

    fun emptyGroud(roundCount: Int): Int {
        var minx = elves.first().x
        var maxx = elves.first().x
        var miny = elves.first().y
        var maxy = elves.first().y
        for (round in 0 until roundCount) {

            //proposition
            val elveProp = mutableMapOf<IntCoordinates, IntCoordinates>()
            val propCount = mutableMapOf<IntCoordinates, Int>()
            for (elve in elves) {
                if (
                    WindRose.n(elve) in elves ||
                    WindRose.ne(elve) in elves ||
                    WindRose.e(elve) in elves ||
                    WindRose.se(elve) in elves ||
                    WindRose.s(elve) in elves ||
                    WindRose.sw(elve) in elves ||
                    WindRose.w(elve) in elves ||
                    WindRose.nw(elve) in elves
                ) {
                    var proposition: IntCoordinates? = null
                    var indeces = ""

                    for (i in 0 until rules.size) {
                        if (proposition == null) {
                            indeces += ((round + i) % rules.size).toString() + "-"
                            proposition = rules[(round + i) % rules.size](elve, elves)
                        }

                    }
                    //println(indeces)
                    if (proposition != null) {
                        elveProp[elve] = proposition
                        propCount[proposition] = (propCount[proposition] ?: 0) + 1
                    }
                }
            }
            //moving
            val newElves = mutableSetOf<IntCoordinates>()
            for (elve in elves) {
                if (elveProp[elve] != null) {
                    val prop = elveProp[elve]!!
                    if ((propCount[prop] ?: 0) == 1) {
                        newElves.add(prop)
                    } else {
                        newElves.add(elve)
                    }
                } else {
                    newElves.add(elve)
                }
            }
            elves = newElves

        }

        for (elve in elves) {
            if (elve.x < minx) minx = elve.x
            if (elve.x > maxx) maxx = elve.x
            if (elve.y < miny) miny = elve.y
            if (elve.y > maxy) maxy = elve.y
        }

        println("$minx, $maxx, $miny, $maxy")
        return (maxx - minx + 1) * (maxy - miny + 1) - elves.size

    }

    companion object {

        fun run(fileName: String) {

            println( fileName)
        }

    }
}
