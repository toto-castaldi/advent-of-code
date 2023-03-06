import com.toto_castaldi.common.WindRose
import com.toto_castaldi.common.structure.IntCoordinates
import com.toto_castaldi.common.structure.Matrix2D

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
        for (round in 0 until roundCount) {
            //proposition
            val elveProp = mutableMapOf<IntCoordinates, IntCoordinates>()
            val propCount = mutableMapOf<IntCoordinates, Int>()
            for (elve in elves) {
                var proposition: IntCoordinates? = null
                for (i in 0 until rules.size) {
                    if (proposition == null) {
                        proposition = rules[(roundCount + i) % rules.size](elve, elves)
                    }
                }
                if (proposition != null) {
                    elveProp[elve] = proposition
                    propCount[proposition] = (propCount[proposition] ?: 0) + 1
                }
            }
            //moving
            val newElves = mutableSetOf<IntCoordinates>()
            for (elve in elves) {
                if (elveProp[elve] != null) {
                    val prop = elveProp[elve]!!
                    if (propCount[prop] ?: 0 == 1) {
                        newElves.add(elve)
                    }
                }
            }
            elves = newElves
            debug(elves)
        }


        //count
        var total = 0
        for (elve in elves) {
            if (WindRose.n(elve) !in elves) total ++
            if (WindRose.ne(elve) !in elves) total ++
            if (WindRose.e(elve) !in elves) total ++
            if (WindRose.se(elve) !in elves) total ++
            if (WindRose.s(elve) !in elves) total ++
            if (WindRose.sw(elve) !in elves) total ++
            if (WindRose.w(elve) !in elves) total ++
            if (WindRose.nw(elve) !in elves) total ++
        }

        return total
    }

    private fun debug(elves: MutableSet<IntCoordinates>) {
        val matrix2D = Matrix2D(10, 10, '.')
        for (elve in elves) {

        }
    }

    companion object {

        fun run(fileName: String) {

            println( fileName)
        }

    }
}
