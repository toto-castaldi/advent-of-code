import com.toto_castaldi.common.WindRose
import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File

class Aoc202223() {

    private var elveMoved = false

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
        for (round in 0 until roundCount) {
            round(round)
        }

        val minx = elves.minBy { it.x }.x
        val maxx = elves.maxBy { it.x }.x
        val miny = elves.minBy { it.y }.y
        val maxy = elves.maxBy { it.y }.y

        return (maxx - minx + 1) * (maxy - miny + 1) - elves.size

    }

    private fun round(round: Int) {
        //proposition
        elveMoved = false
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

                for (i in 0 until rules.size) {
                    if (proposition == null) {
                        proposition = rules[(round + i) % rules.size](elve, elves)
                    }
                }
                if (proposition != null) {
                    elveProp[elve] = proposition
                    propCount[proposition] = (propCount[proposition] ?: 0) + 1
                }
            }
        }
        //moving
        val newElves = mutableSetOf<IntCoordinates>()
        for (elve in elves) {
            var el = elve
            if (elveProp[elve] != null) {
                val prop = elveProp[elve]!!
                if ((propCount[prop] ?: 0) == 1) {
                    elveMoved = true
                    el = prop
                }
            }
            newElves.add(el)
        }
        elves = newElves
    }

    fun roundNumberWithElvesStuck(): Int {
        var result = 0
        elveMoved = true
        while (elveMoved) {
            round(result)
            result ++
        }
        return result
    }

    companion object {

        fun run1(fileName: String) {
            val aoc = Aoc202223()
            var y = 0
            File(fileName).forEachLine {
                aoc.map(y++, it)
            }
            println( aoc.emptyGroud(10))
        }

        fun run2(fileName: String) {
            val aoc = Aoc202223()
            var y = 0
            File(fileName).forEachLine {
                aoc.map(y++, it)
            }
            println( aoc.roundNumberWithElvesStuck())
        }

    }
}
