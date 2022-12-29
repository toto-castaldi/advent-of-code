import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Matrix2D

class Aoc202222Part2(val cubeMap: CubeMap) : Aoc202222() {
    private lateinit var currentFace: Matrix2D<MapPoint>
    private lateinit var wF: Matrix2D<MapPoint>
    private lateinit var bF: Matrix2D<MapPoint>
    private lateinit var oF: Matrix2D<MapPoint>
    private lateinit var gF: Matrix2D<MapPoint>
    private lateinit var yF: Matrix2D<MapPoint>
    private lateinit var rF: Matrix2D<MapPoint>
    private var cubeFaceWidth = Integer.MAX_VALUE

    override fun resolveMap(): Matrix2D<MapPoint> {
        return currentFace
    }

    override operator fun plus(mapLine: String) {
        super.plus(mapLine)
        if (mapLine.trim().length < cubeFaceWidth) cubeFaceWidth = mapLine.trim().length

        if (rawMap.ny == cubeFaceWidth * 3) {
            wF = cubeMap.white(rawMap, cubeFaceWidth)
            bF = cubeMap.blu(rawMap, cubeFaceWidth)
            oF = cubeMap.orange(rawMap, cubeFaceWidth)
            gF = cubeMap.green(rawMap, cubeFaceWidth)
            yF = cubeMap.yellow(rawMap, cubeFaceWidth)
            rF = cubeMap.red(rawMap, cubeFaceWidth)
            currentFace = wF
        }
    }

    override fun nextSpot(x: Int, y: Int, direction: CrossDirection): NavigationProposal {
        val map = currentFace

        val cubeMapNavigation = cubeMap.navigate(map, direction, x, y, wF, bF, oF, gF, yF, rF)

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
        return format(cubeMap.popolate(Matrix2D(rawMapWidth, rawMapLines.size, MapPoint.EMPTY ), cubeFaceWidth, wF, bF, oF, gF, yF, rF))
    }


    override fun proposalApplied(proposal: NavigationProposal) {
        currentFace = proposal.map
    }


    interface CubeMap {
        fun white(rawData: Matrix2D<MapPoint>, edge: Int): Matrix2D<MapPoint>
        fun orange(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun green(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun blu(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun red(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun yellow(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun navigate(
            face: Matrix2D<MapPoint>,
            direction: CrossDirection,
            iX: Int,
            iY: Int,
            w : Matrix2D<MapPoint>,
            b : Matrix2D<MapPoint>,
            o : Matrix2D<MapPoint>,
            g : Matrix2D<MapPoint>,
            y : Matrix2D<MapPoint>,
            r : Matrix2D<MapPoint>
        ): CubeMapNavigation

        fun popolate(matrix: Matrix2D<MapPoint>,
                     cubeFaceWidth : Int,
                     w : Matrix2D<MapPoint>,
                     b : Matrix2D<MapPoint>,
                     o : Matrix2D<MapPoint>,
                     g : Matrix2D<MapPoint>,
                     y : Matrix2D<MapPoint>,
                     r : Matrix2D<MapPoint>): Matrix2D<MapPoint>

        fun finalRow(face : Matrix2D<MapPoint>, y: Int): Int
        fun finalColumn(face : Matrix2D<MapPoint>, x: Int): Int

        data class CubeMapNavigation(val x: Int, val y: Int, val direction: CrossDirection, val newFace : Matrix2D<MapPoint>)
    }

    override fun finalColumn(x: Int): Int {
        return cubeMap.finalColumn(currentFace, x)
    }

    override fun finalRow(y: Int): Int {
        return cubeMap.finalRow(currentFace, y)
    }

    companion object {
        val EXAMPLE_MAP: CubeMap = object : CubeMap {
            override fun white(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(len * 2,0, len, len)
            }

            override fun orange(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(0,len, len, len)
            }

            override fun green(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(len ,len, len, len)
            }

            override fun red(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(len * 2,len , len, len)
            }

            override fun yellow(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(len * 2,len * 2, len, len)
            }

            override fun blu(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                end = len - 1
                return rawData.sub(len * 3,len * 2, len , len)
            }

            override fun navigate(
                face: Matrix2D<MapPoint>,
                direction: CrossDirection,
                iX: Int,
                iY: Int,
                w : Matrix2D<MapPoint>,
                b : Matrix2D<MapPoint>,
                o : Matrix2D<MapPoint>,
                g : Matrix2D<MapPoint>,
                y : Matrix2D<MapPoint>,
                r : Matrix2D<MapPoint>
            ): CubeMap.CubeMapNavigation {
                return when(face) {
                    w -> {
                        if (direction == CrossDirection.R) return CubeMap.CubeMapNavigation(end, fromEnd(iX), CrossDirection.L, r)
                        if (direction == CrossDirection.D) return CubeMap.CubeMapNavigation(iX, 0, CrossDirection.D, r)
                        throw Exception("invalid dir $direction")
                    }
                    b -> {
                        if (direction == CrossDirection.D) return CubeMap.CubeMapNavigation(0, iX, CrossDirection.L, o)
                        if (direction == CrossDirection.L) return CubeMap.CubeMapNavigation(end, iY, CrossDirection.L, y)
                        throw Exception("invalid dir $direction")
                    }
                    o -> {
                        if (direction == CrossDirection.U) return CubeMap.CubeMapNavigation(fromEnd(iY), 0, CrossDirection.D, w)
                        if (direction == CrossDirection.R) return CubeMap.CubeMapNavigation(0, iY, CrossDirection.R, g)
                        throw Exception("invalid dir $direction")
                    }
                    g -> {
                        if (direction == CrossDirection.R) return CubeMap.CubeMapNavigation(0, iY, CrossDirection.R, r)
                        if (direction == CrossDirection.U) return CubeMap.CubeMapNavigation(0, iX, CrossDirection.R, w)
                        throw Exception("invalid dir $direction")
                    }
                    y -> {
                        if (direction == CrossDirection.L) return CubeMap.CubeMapNavigation(fromEnd(iY), end, CrossDirection.D, g)
                        if (direction == CrossDirection.D) return CubeMap.CubeMapNavigation(fromEnd(iX), end, CrossDirection.U, o)
                        throw Exception("invalid dir $direction")
                    }
                    r -> {
                        if (direction == CrossDirection.D) return CubeMap.CubeMapNavigation(iX, 0, CrossDirection.D, y)
                        if (direction == CrossDirection.R) return CubeMap.CubeMapNavigation(fromEnd(iY), 0, CrossDirection.D, b)

                        throw Exception("invalid dir $direction")
                    }
                    else -> throw Exception("invalid $face")
                }
            }

            override fun popolate(
                matrix: Matrix2D<MapPoint>,
                cubeFaceWidth : Int,
                w : Matrix2D<MapPoint>,
                b : Matrix2D<MapPoint>,
                o : Matrix2D<MapPoint>,
                g : Matrix2D<MapPoint>,
                y : Matrix2D<MapPoint>,
                r : Matrix2D<MapPoint>): Matrix2D<MapPoint> {
                return matrix
                    .putSubmap(2*cubeFaceWidth, 0, w)
                    .putSubmap(0, cubeFaceWidth, o)
                    .putSubmap(cubeFaceWidth, cubeFaceWidth, g)
                    .putSubmap(cubeFaceWidth * 2, cubeFaceWidth, r)
                    .putSubmap(cubeFaceWidth * 2, cubeFaceWidth * 2, y)
                    .putSubmap(cubeFaceWidth * 3, cubeFaceWidth * 2, b)
            }

            override fun finalRow(face : Matrix2D<MapPoint>, y: Int): Int {
                return y + (end + 1)
            }

            override fun finalColumn(face: Matrix2D<MapPoint>, x: Int): Int {
                return x + (end + 1)
            }

            private var end: Int = 0

            val fromEnd = { v : Int -> end - v }
        }
    }

}
