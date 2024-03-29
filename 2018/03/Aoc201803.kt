import com.toto_castaldi.common.structure.IntCoordinates
import java.io.File
import java.lang.Error

class Aoc201803() {

    private val claims = mutableListOf<Claim>()
    private val points = mutableMapOf<IntCoordinates, Int>()

    
    operator fun plus(s: String) {
        //#1 @ 1,3: 4x4
        val claimId = s.split(" @ ")[0].substring(1).toInt() //1
        val startingPoint = s.split(" @ ")[1].split(":")[0].let {
            val split = it.split(",")
            IntCoordinates(split[0].trim().toInt(),split[1].trim().toInt())
        }
        val dimension = s.split(": ")[1].let {
            val split = it.split("x")
            IntCoordinates(split[0].trim().toInt(),split[1].trim().toInt())
        }

        claims.add(Claim(claimId, startingPoint, dimension))

        for (x in 1..dimension.x) {
            for (y in 1..dimension.y) {
                val p = IntCoordinates(startingPoint.x + x, startingPoint.y + y)
                points[p] = points[p]?.plus(1) ?: 1
            }
        }

    }

    data class Claim(val claimId: Int, val startingPoint: IntCoordinates, val dimension: IntCoordinates)

    fun squareInchesWithTwoOrMoreClaims(): Int {
        return points.values.count { it > 1 }
    }

    fun idWithNoOtherOverlapping(): Int {
        for (claim in claims) {
            val dimension = claim.dimension
            val startingPoint = claim.startingPoint
            var notOverlapping = true
            for (x in 1..dimension.x) {
                for (y in 1..dimension.y) {
                    val p = IntCoordinates(startingPoint.x + x, startingPoint.y + y)
                    if (points[p]!! > 1) {
                        notOverlapping = false
                    }
                }
            }
            if (notOverlapping) return claim.claimId
        }
        throw Error("not found")
    }

    companion object {
        fun run1(fileName: String) {
            val aoc = Aoc201803()
            for (line in File(fileName).readLines()) {
                aoc + line
            }
            println(aoc.squareInchesWithTwoOrMoreClaims())
        }

        fun run2(fileName: String) {
            val aoc = Aoc201803()
            for (line in File(fileName).readLines()) {
                aoc + line
            }
            println(aoc.idWithNoOtherOverlapping())
        }
    }

}
