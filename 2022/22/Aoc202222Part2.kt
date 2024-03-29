import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.IntCoordinates
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

        if (rawMap.ny == cubeFaceWidth * cubeMap.inputHeight()) {
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
        val wc : IntCoordinates,
        val oc : IntCoordinates,
        val gc : IntCoordinates,
        val rc : IntCoordinates,
        val yc : IntCoordinates,
        val bc : IntCoordinates
        ) {
        internal lateinit var w: Matrix2D<MapPoint>
        internal lateinit var o: Matrix2D<MapPoint>
        internal lateinit var g: Matrix2D<MapPoint>
        internal lateinit var r: Matrix2D<MapPoint>
        internal lateinit var y: Matrix2D<MapPoint>
        internal lateinit var b: Matrix2D<MapPoint>

        private var len = 0

        fun first(): Matrix2D<MapPoint> {
            return w
        }

        fun popolate(matrix: Matrix2D<MapPoint>): Matrix2D<MapPoint> {
            return matrix
                .putSubmap(wc.x * len, wc.y * len, w)
                .putSubmap(oc.x * len, oc.y * len, o)
                .putSubmap(gc.x * len, gc.y * len, g)
                .putSubmap(rc.x * len, rc.y * len, r)
                .putSubmap(yc.x * len, yc.y * len, y)
                .putSubmap(bc.x * len, bc.y * len, b)
        }

        fun coord(face : Matrix2D<MapPoint>) : IntCoordinates {
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

        abstract fun navigate(
            face: Matrix2D<MapPoint>,
            direction: CrossDirection,
            iX: Int,
            iY: Int
        ): CubeMapNavigation

        fun inputHeight(): Int {
            return setOf(wc.y, oc.y, gc.y, rc.y, yc.y, bc.y).max() + 1
        }

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
            IntCoordinates(1,0),
            IntCoordinates(0,2),
            IntCoordinates(1,1),
            IntCoordinates(2,0),
            IntCoordinates(1,2),
            IntCoordinates(0,3)
        ) {

            override fun navigate(
                face: Matrix2D<MapPoint>,
                direction: CrossDirection,
                iX: Int,
                iY: Int
            ): CubeMapNavigation {
                return when(face) {
                    w -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(0,iX,CrossDirection.R,b)
                        CrossDirection.R -> CubeMapNavigation(0,iY,CrossDirection.R,r)
                        CrossDirection.D -> CubeMapNavigation(iX,0,CrossDirection.D,g)
                        CrossDirection.L -> CubeMapNavigation(0,opposite(iY),CrossDirection.R,o)
                    }
                    r -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(iX,edge(),CrossDirection.U,b)
                        CrossDirection.R -> CubeMapNavigation(edge(),opposite(iY),CrossDirection.L,y)
                        CrossDirection.D -> CubeMapNavigation(edge(),iX,CrossDirection.L,g)
                        CrossDirection.L -> CubeMapNavigation(edge(),iY,CrossDirection.L,w)
                    }
                    g -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(iX,edge(),CrossDirection.U,w)
                        CrossDirection.R -> CubeMapNavigation(iY,edge(),CrossDirection.U,r)
                        CrossDirection.D -> CubeMapNavigation(iX,0,CrossDirection.D,y)
                        CrossDirection.L -> CubeMapNavigation(iY,0,CrossDirection.D,o)
                    }
                    o -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(0,iX,CrossDirection.R,g)
                        CrossDirection.R -> CubeMapNavigation(0,iY,CrossDirection.R,y)
                        CrossDirection.D -> CubeMapNavigation(iX,0,CrossDirection.D,b)
                        CrossDirection.L -> CubeMapNavigation(0,opposite(iY),CrossDirection.R,w)
                    }
                    y -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(iX,edge(),CrossDirection.U,g)
                        CrossDirection.R -> CubeMapNavigation(edge(),opposite(iY),CrossDirection.L,r)
                        CrossDirection.D -> CubeMapNavigation(edge(),iX,CrossDirection.L,b)
                        CrossDirection.L -> CubeMapNavigation(edge(),iY,CrossDirection.L,o)
                    }
                    b -> when(direction) {
                        CrossDirection.U -> CubeMapNavigation(iX,edge(),CrossDirection.U,o)
                        CrossDirection.R -> CubeMapNavigation(iY,edge(),CrossDirection.U,y)
                        CrossDirection.D -> CubeMapNavigation(iX,0,CrossDirection.D,r)
                        CrossDirection.L -> CubeMapNavigation(iY,0,CrossDirection.D,w)
                    }
                    else -> throw Exception("unknown $face")
                }

            }

        }

        val EXAMPLE_MAP = object: CubeMap(
                IntCoordinates(2,0),
                IntCoordinates(0,1),
                IntCoordinates(1,1),
                IntCoordinates(2,1),
                IntCoordinates(2,2),
                IntCoordinates(3,2)
            ) {

            override fun navigate(
                face: Matrix2D<MapPoint>,
                direction: CrossDirection,
                iX: Int,
                iY: Int
            ): CubeMapNavigation {
                return when(face) {
                    w -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(iX, opposite(iY), CrossDirection.L, b)
                            CrossDirection.D -> CubeMapNavigation(iX, 0, CrossDirection.D, r)
                            CrossDirection.U -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.L -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                        }
                    }
                    o -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(0, iY, CrossDirection.R, g)
                            CrossDirection.D -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.U -> CubeMapNavigation(opposite(iX), 0, CrossDirection.D, w)
                            CrossDirection.L -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                        }
                    }
                    g -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(0, iY, CrossDirection.R, r)
                            CrossDirection.D -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.U -> CubeMapNavigation(0, iX, CrossDirection.R, w)
                            CrossDirection.L -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                        }
                    }
                    r -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(opposite(iY), 0, CrossDirection.D, b)
                            CrossDirection.D -> CubeMapNavigation(iX, 0, CrossDirection.D, y)
                            CrossDirection.U -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.L -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                        }
                    }
                    y -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.D -> CubeMapNavigation(opposite(iX), edge(), CrossDirection.U, o)
                            CrossDirection.U -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.L -> CubeMapNavigation(opposite(iX), edge(), CrossDirection.U, g)
                        }
                    }
                    b -> {
                        when (direction) {
                            CrossDirection.R -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.D -> CubeMapNavigation(0, opposite(iX), CrossDirection.L, o)
                            CrossDirection.U -> CubeMapNavigation(0, 0, CrossDirection.U, face)
                            CrossDirection.L -> CubeMapNavigation(edge(), iY, CrossDirection.L, y)
                        }
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
