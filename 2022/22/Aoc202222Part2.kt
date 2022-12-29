import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Rubik

class Aoc202222Part2(val cubeMap: CubeMap) : Aoc202222() {
    private lateinit var rubik : Rubik<Matrix2D<MapPoint>>
    private var cubeFaceWidth = Integer.MAX_VALUE
    

    override fun resolveMap(): Matrix2D<MapPoint> {
        return rubik.currentFront()
    }

    override operator fun plus(mapLine: String) {
        super.plus(mapLine)
        if (mapLine.trim().length < cubeFaceWidth) cubeFaceWidth = mapLine.trim().length

        if (rawMap.ny == cubeFaceWidth * 3) {
            val w = cubeMap.white(rawMap, cubeFaceWidth)
            val b = cubeMap.blu(rawMap, cubeFaceWidth)
            val o = cubeMap.orange(rawMap, cubeFaceWidth)
            rubik = Rubik(
                b,
                w,
                cubeMap.green(rawMap, cubeFaceWidth),
                cubeMap.yellow(rawMap, cubeFaceWidth),
                cubeMap.red(rawMap, cubeFaceWidth),
                o
            )
            rubik.set(w, o)
        }
    }

    override fun nextSpot(x: Int, y: Int, direction: CrossDirection): NavigationProposal {
        val map = rubik.currentFront()

        val newRubik = Rubik(rubik.blu, rubik.white, rubik.green, rubik.yellow, rubik.red, rubik.orange)
        newRubik.set(rubik.currentFront(), rubik.currentUp())

        return when (direction) {
            CrossDirection.R -> {
                if (x + 1 == map.nx) {
                    newRubik.rotateRight()
                    newRubik.rotateUp()
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    RubikNavigationProposal.build(newRubik, x + 1 , y, direction)
                }
            }
            CrossDirection.L -> {
                if (x == 0) {
                    newRubik.rotateUp(-1)
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    RubikNavigationProposal.build(newRubik, x - 1 , y, direction)
                }
            }
            CrossDirection.D -> {
                if (y + 1 == map.ny) {
                    newRubik.rotateRight()
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    RubikNavigationProposal.build(newRubik, x , y + 1, direction)
                }
            }
            CrossDirection.U -> {
                if (y == 0) {
                    newRubik.rotateRight(-1)
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    RubikNavigationProposal.build(newRubik, x , y - 1, direction)
                }
            }
        }
    }

    override fun formatMap(): String {
        return format(cubeMap.popolate(Matrix2D(rawMapWidth, rawMapLines.size, MapPoint.EMPTY ), cubeFaceWidth))
    }

    class RubikNavigationProposal private constructor (
        val newRubik: Rubik<Matrix2D<MapPoint>>,
        x: Int,
        y: Int,
        direction: CrossDirection) : NavigationProposal(newRubik.currentFront(), x, y, direction ) {

        companion object {

            fun build(
                cubeMap: CubeMap,
                r: Rubik<Matrix2D<MapPoint>>,
                newR: Rubik<Matrix2D<MapPoint>>,
                x: Int,
                y: Int
            ): RubikNavigationProposal {
                val navigation = cubeMap.navigate(r.currentFront(), newR.currentFront(), x, y)
                return RubikNavigationProposal(
                    newR,
                    navigation.x,
                    navigation.y,
                    navigation.direction
                )
            }

            fun build(
                rubik: Rubik<Matrix2D<MapPoint>>,
                x: Int,
                y: Int,
                direction: CrossDirection
            ): RubikNavigationProposal {
                return RubikNavigationProposal(
                    rubik,
                    x,
                    y,
                    direction
                )
            }
        }

    }

    override fun proposalApplied(proposal: NavigationProposal) {
        proposal as RubikNavigationProposal
        rubik.set(proposal.newRubik.currentFront(), proposal.newRubik.currentUp())
    }


    interface CubeMap {
        fun white(rawData: Matrix2D<MapPoint>, edge: Int): Matrix2D<MapPoint>
        fun orange(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun green(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun blu(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun red(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun yellow(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun navigate(
            currentFront: Matrix2D<MapPoint>,
            currentFront1: Matrix2D<MapPoint>,
            x: Int,
            y: Int
        ): CubeMapNavigation

        fun popolate(matrix: Matrix2D<MapPoint>, faceWidth : Int): Matrix2D<MapPoint>

        data class CubeMapNavigation(val x: Int, val y: Int, val direction: CrossDirection)
    }

    companion object {
        val EXAMPLE_MAP: CubeMap = object : CubeMap {
            private lateinit var r: Matrix2D<MapPoint>
            private lateinit var w: Matrix2D<MapPoint>
            private lateinit var b: Matrix2D<MapPoint>
            private lateinit var o: Matrix2D<MapPoint>
            private lateinit var g: Matrix2D<MapPoint>
            private lateinit var y: Matrix2D<MapPoint>

            override fun white(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                w = rawData.sub(len * 2,0, len, len)
                return w
            }

            override fun orange(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                o = rawData.sub(0,len, len, len)
                return o
            }

            override fun green(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                g = rawData.sub(len ,len, len, len)
                return g
            }

            override fun red(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                r = rawData.sub(len * 2,len , len, len)
                return r
            }

            override fun yellow(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                y = rawData.sub(len * 2,len * 2, len, len)
                return y
            }

            override fun blu(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                b = rawData.sub(len * 3,len * 2, len , len)
                return b
            }

            override fun navigate(
                faceFrom: Matrix2D<MapPoint>,
                faceTo: Matrix2D<MapPoint>,
                ix: Int,
                iy: Int
            ): CubeMap.CubeMapNavigation {
                return when(faceFrom) {
                    r -> {
                        if (faceTo == w) return CubeMap.CubeMapNavigation(ix, endY(w), CrossDirection.U)
                        if (faceTo == o) return CubeMap.CubeMapNavigation(0, iy, CrossDirection.R)
                        if (faceTo == b) return CubeMap.CubeMapNavigation(fromEndY(r, iy), 0, CrossDirection.D)
                        throw Exception("invalid $faceTo")
                    }
                    w -> {
                        if (faceTo == r) return CubeMap.CubeMapNavigation(ix, 0, CrossDirection.D)
                        throw Exception("invalid $faceTo")
                    }
                    b -> {
                        if (faceTo == y) return CubeMap.CubeMapNavigation(endX(y), iy, CrossDirection.L)
                        throw Exception("invalid $faceTo")
                    }
                    y -> {
                        if (faceTo == o) return CubeMap.CubeMapNavigation(fromEndX(y, ix), endY(o), CrossDirection.U)
                        throw Exception("invalid $faceTo")
                    }
                    o -> {
                        if (faceTo == g) return CubeMap.CubeMapNavigation(0, iy, CrossDirection.R)
                        throw Exception("invalid $faceTo")
                    }
                    else -> throw Exception("invalid $faceFrom")
                }
            }

            override fun popolate(matrix: Matrix2D<MapPoint>, cubeFaceWidth : Int): Matrix2D<MapPoint> {
                return matrix
                    .putSubmap(2*cubeFaceWidth, 0, w)
                    .putSubmap(0, cubeFaceWidth, o)
                    .putSubmap(cubeFaceWidth, cubeFaceWidth, g)
                    .putSubmap(cubeFaceWidth * 2, cubeFaceWidth, r)
                    .putSubmap(cubeFaceWidth * 2, cubeFaceWidth * 2, y)
                    .putSubmap(cubeFaceWidth * 3, cubeFaceWidth * 2, b)
            }

            val endY = { m: Matrix2D<MapPoint> -> m.ny - 1 }
            val endX = { m: Matrix2D<MapPoint> -> m.nx - 1 }
            val fromEndY = { m: Matrix2D<MapPoint>, y : Int -> m.ny - y - 1}
            val fromEndX = { m: Matrix2D<MapPoint>, x : Int -> m.nx - x - 1}

        }
    }

}
