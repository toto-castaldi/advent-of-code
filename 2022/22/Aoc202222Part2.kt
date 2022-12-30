import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import java.io.File

class Aoc202222Part2(val cubeMap: CubeMap) : Aoc202222() {
    private lateinit var currentFace: Matrix2D<MapPoint>
    private var cubeFaceWidth = Integer.MAX_VALUE

    override fun resolveMap(): Matrix2D<MapPoint> {
        return currentFace
    }

    override operator fun plus(mapLine: String) {
        super.plus(mapLine)
        if (mapLine.trim().length < cubeFaceWidth) cubeFaceWidth = mapLine.trim().length

        if (rawMap.ny == cubeFaceWidth * 3) {
            cubeMap.parse(rawMap, cubeFaceWidth)
            currentFace = cubeMap.first()
        }
    }

    override fun nextSpot(x: Int, y: Int, direction: CrossDirection): NavigationProposal {
        val map = currentFace

        val cubeMapNavigation = cubeMap.navigate(map, direction, x, y)

        return when (direction) {
            CrossDirection.R -> {
                if (x + 1 == map.nx) {
                    NavigationProposal(cubeMapNavigation.newFace, cubeMapNavigation.x, cubeMapNavigation.y, cubeMapNavigation.direction)
                } else {
                    NavigationProposal(map, x + 1 , y, direction)
                }
            }
            CrossDirection.L -> {
                if (x == 0) {
                    NavigationProposal(cubeMapNavigation.newFace, cubeMapNavigation.x, cubeMapNavigation.y, cubeMapNavigation.direction)
                } else {
                    NavigationProposal(map, x - 1 , y, direction)
                }
            }
            CrossDirection.D -> {
                if (y + 1 == map.ny) {
                    NavigationProposal(cubeMapNavigation.newFace, cubeMapNavigation.x, cubeMapNavigation.y, cubeMapNavigation.direction)
                } else {
                    NavigationProposal(map, x , y + 1, direction)
                }
            }
            CrossDirection.U -> {
                if (y == 0) {
                    NavigationProposal(cubeMapNavigation.newFace, cubeMapNavigation.x, cubeMapNavigation.y, cubeMapNavigation.direction)
                } else {
                    NavigationProposal(map, x , y - 1, direction)
                }
            }
        }
    }

    override fun formatMap(): String {
        return format(cubeMap.popolate(Matrix2D(rawMapWidth, rawMapLines.size, MapPoint.EMPTY )))
    }


    override fun proposalApplied(proposal: NavigationProposal) {
        currentFace = proposal.map
    }


    abstract class CubeMap(
        val wc : Coordinates,
        val oc : Coordinates,
        val gc : Coordinates,
        val rc : Coordinates,
        val yc : Coordinates,
        val bc : Coordinates
        ) {


        internal lateinit var w: Matrix2D<MapPoint>
        internal lateinit var o: Matrix2D<MapPoint>
        internal lateinit var g: Matrix2D<MapPoint>
        internal lateinit var r: Matrix2D<MapPoint>
        internal lateinit var y: Matrix2D<MapPoint>
        internal lateinit var b: Matrix2D<MapPoint>

        private var len = 0

        fun popolate(matrix: Matrix2D<MapPoint>): Matrix2D<MapPoint> {
            return matrix
                .putSubmap(wc.x * len, wc.y * len, w)
                .putSubmap(oc.x * len, oc.y * len, o)
                .putSubmap(gc.x * len, gc.y * len, g)
                .putSubmap(rc.x * len, rc.y * len, r)
                .putSubmap(yc.x * len, yc.y * len, y)
                .putSubmap(bc.x * len, bc.y * len, b)
        }

        fun coord(face : Matrix2D<MapPoint>) : Coordinates {
            return when (face) {
                w -> wc
                o -> oc
                g -> gc
                r -> rc
                y -> yc
                b -> bc
                else -> throw Exception("unknown face $face")
            }
        }

        fun finalRow(face : Matrix2D<MapPoint>, iY: Int): Int {
            return coord(face).y * len + iY
        }
        fun finalColumn(face : Matrix2D<MapPoint>, iX: Int): Int {
            return coord(face).x * len + iX
        }
        fun parse(rawData: Matrix2D<MapPoint>, l: Int) {
            len = l
            w = rawData.sub(wc.x * len,wc.y * len, len, len)
            o = rawData.sub(oc.x * len,oc.y * len, len, len)
            g = rawData.sub(gc.x * len,gc.y * len, len, len)
            r = rawData.sub(rc.x * len,rc.y * len, len, len)
            y = rawData.sub(yc.x * len,yc.y * len, len, len)
            b = rawData.sub(bc.x * len,bc.y * len, len, len)
        }

        val opposite = { v : Int -> len - 1 - v }
        val edge = { len - 1 }

        abstract fun first(): Matrix2D<MapPoint>

        abstract fun navigate(
            face: Matrix2D<MapPoint>,
            direction: CrossDirection,
            iX: Int,
            iY: Int
        ): CubeMapNavigation

        data class CubeMapNavigation(val x: Int, val y: Int, val direction: CrossDirection, val newFace : Matrix2D<MapPoint>)
    }

    override fun finalColumn(x: Int): Int {
        return cubeMap.finalColumn(currentFace, x)
    }

    override fun finalRow(y: Int): Int {
        return cubeMap.finalRow(currentFace, y)
    }

    companion object {
        val REAL_MAP = object: CubeMap(
            Coordinates(0,0),
            Coordinates(0,0),
            Coordinates(0,0),
            Coordinates(0,0),
            Coordinates(0,0),
            Coordinates(0,0)
        ) {
            override fun first(): Matrix2D<MapPoint> {
                TODO("Not yet implemented")
            }

            override fun navigate(
                face: Matrix2D<MapPoint>,
                direction: CrossDirection,
                iX: Int,
                iY: Int
            ): CubeMapNavigation {
                TODO("Not yet implemented")
            }

        }

        val EXAMPLE_MAP = object: CubeMap(
                Coordinates(2,0),
                Coordinates(0,1),
                Coordinates(1,1),
                Coordinates(2,1),
                Coordinates(2,2),
                Coordinates(3,2)
            ) {
            override fun first(): Matrix2D<MapPoint> {
                return w
            }

            override fun navigate(
                face: Matrix2D<MapPoint>,
                direction: CrossDirection,
                iX: Int,
                iY: Int
            ): CubeMapNavigation {
                return when(face) {
                    w -> {
                        if (direction == CrossDirection.R) return CubeMapNavigation(iX, opposite(iY), CrossDirection.L, b)
                        if (direction == CrossDirection.D) return CubeMapNavigation(iX, 0, CrossDirection.D, r)
                        throw Exception("invalid dir $direction")
                    }
                    o -> {
                        if (direction == CrossDirection.U) return CubeMapNavigation(opposite(iX), 0, CrossDirection.D, w)
                        if (direction == CrossDirection.R) return CubeMapNavigation(0, iY, CrossDirection.R, g)
                        throw Exception("invalid dir $direction")
                    }
                    g -> {
                        if (direction == CrossDirection.R) return CubeMapNavigation(0, iY, CrossDirection.R, r)
                        if (direction == CrossDirection.U) return CubeMapNavigation(0, iX, CrossDirection.R, w)
                        throw Exception("invalid dir $direction")
                    }
                    r -> {
                        if (direction == CrossDirection.D) return CubeMapNavigation(iX, 0, CrossDirection.D, y)
                        if (direction == CrossDirection.R) return CubeMapNavigation(opposite(iY), 0, CrossDirection.D, b)
                        throw Exception("invalid dir $direction")
                    }
                    y -> {
                        if (direction == CrossDirection.L) return CubeMapNavigation(opposite(iX), edge(), CrossDirection.U, g)
                        if (direction == CrossDirection.D) return CubeMapNavigation(opposite(iX), edge(), CrossDirection.U, o)
                        throw Exception("invalid dir $direction")
                    }
                    b -> {
                        if (direction == CrossDirection.D) return CubeMapNavigation(0, opposite(iX), CrossDirection.L, o)
                        if (direction == CrossDirection.L) return CubeMapNavigation(edge(), iY, CrossDirection.L, y)
                        throw Exception("invalid dir $direction")
                    }
                    else -> throw Exception("invalid $face")
                }
            }
        }

        fun run(fileName: String, test : Boolean) {
            val aoc = Aoc202222Part2(if (test) EXAMPLE_MAP else REAL_MAP)
            var map = true
            File(fileName).forEachLine {
                if (map) aoc + it else aoc.navigate(it)
                if (it.isBlank()) map = false
            }
            println( aoc.finalPassword())
        }
    }

}
