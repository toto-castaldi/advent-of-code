import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Matrix2D
import java.io.File

class Aoc202222Part1() : Aoc202222() {

    override fun nextSpot(x : Int, y : Int, direction: CrossDirection): NavigationProposal {
        return when (direction) {
            CrossDirection.R -> {
                if (x + 1 == rawMap.nx || rawMap[x + 1, y] == MapPoint.EMPTY) {
                    var i = 0
                    while (rawMap[i, y] == MapPoint.EMPTY) i++
                    NavigationProposal(rawMap, i, y, direction)
                } else {
                    NavigationProposal(rawMap, x + 1, y, direction)
                }
            }

            CrossDirection.L -> {
                if (x == 0 || rawMap[x - 1, y] == MapPoint.EMPTY) {
                    var i = rawMap.nx - 1
                    while (rawMap[i, y] == MapPoint.EMPTY) i--
                    NavigationProposal(rawMap, i, y, direction)
                } else {
                    NavigationProposal(rawMap, x - 1, y, direction)
                }
            }

            CrossDirection.D -> {
                if (y + 1 == rawMap.ny || rawMap[x, y + 1] == MapPoint.EMPTY) {
                    var i = 0
                    while (rawMap[x, i] == MapPoint.EMPTY) i++
                    NavigationProposal(rawMap, x, i, direction)
                } else {
                    NavigationProposal(rawMap, x, y + 1, direction)
                }
            }

            CrossDirection.U -> {
                if (y == 0 || rawMap[x, y - 1] == MapPoint.EMPTY) {
                    var i = rawMap.ny - 1
                    while (rawMap[x, i] == MapPoint.EMPTY) i--
                    NavigationProposal(rawMap, x, i, direction)
                } else {
                    NavigationProposal(rawMap, x, y - 1, direction)
                }
            }
        }
    }

    override fun formatMap(): String {
        return format(rawMap)
    }

    override fun proposalApplied(proposal: NavigationProposal) {

    }

    override fun resolveMap(): Matrix2D<MapPoint> {
        return rawMap
    }

    companion object {
        fun run(fileName: String) {
            val aoc = Aoc202222Part1()
            var map = true
            File(fileName).forEachLine {
                if (map) aoc + it else aoc.navigate(it)
                if (it.isBlank()) map = false
            }
            println( aoc.finalPassword())
        }

    }
}
