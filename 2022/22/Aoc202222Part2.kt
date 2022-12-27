import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Rubik

class Aoc202222Part2(val cubeMap: CubeMap) : Aoc202222() {
    val rubik : Rubik<Matrix2D<MapPoint>>
    var cubeFaceWidth = Integer.MAX_VALUE
    
    init {
        val w = cubeMap.white(rawMap, cubeFaceWidth)
        val b = cubeMap.blu(rawMap, cubeFaceWidth)
        rubik = Rubik(
            b,
            w,
            cubeMap.green(rawMap, cubeFaceWidth),
            cubeMap.yellow(rawMap, cubeFaceWidth),
            cubeMap.red(rawMap, cubeFaceWidth),
            cubeMap.orange(rawMap, cubeFaceWidth)
        )
        rubik.set(w,b)
    }

    override fun resolveMap(): Matrix2D<MapPoint> {
        return rubik.currentFront()
    }

    override operator fun plus(mapLine: String) {
        super.plus(mapLine)
        if (mapLine.trim().length < cubeFaceWidth) cubeFaceWidth = mapLine.trim().length
    }

    override fun nextSpot(x: Int, y: Int, direction: CrossDirection): NavigationProposal {
        val map = rubik.currentFront()

        val newRubik = Rubik(rubik.blu, rubik.white, rubik.green, rubik.yellow, rubik.red, rubik.orange)
        newRubik.set(rubik.currentFront(), rubik.currentUp())

        return when (direction) {
            CrossDirection.R -> {
                if (x + 1 == map.nx) {
                    newRubik.rotateUp()
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    NavigationProposal(newRubik.currentFront(), x + 1 , y, direction)
                }
            }
            CrossDirection.L -> {
                if (x == 0) {
                    newRubik.rotateUp(-1)
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    NavigationProposal(newRubik.currentFront(), x - 1 , y, direction)
                }
            }
            CrossDirection.D -> {
                if (y + 1 == map.ny) {
                    newRubik.rotateRight()
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    NavigationProposal(newRubik.currentFront(), x , y + 1, direction)
                }
            }
            CrossDirection.U -> {
                if (y == 0) {
                    newRubik.rotateRight(-1)
                    RubikNavigationProposal.build(cubeMap, rubik, newRubik, x, y)
                } else {
                    NavigationProposal(newRubik.currentFront(), x , y - 1, direction)
                }
            }
        }
    }

    override fun formatMap(): String {
        return format(
            Matrix2D(rawMapWidth, rawMapLines.size, MapPoint.EMPTY)
                .putSubmap(2*cubeFaceWidth, 0,rubik.white)
                .putSubmap(0, cubeFaceWidth, rubik.blu)
                .putSubmap(cubeFaceWidth, cubeFaceWidth, rubik.orange)
                .putSubmap(cubeFaceWidth * 2, cubeFaceWidth, rubik.green)
                .putSubmap(cubeFaceWidth * 2, cubeFaceWidth * 2, rubik.yellow)
                .putSubmap(cubeFaceWidth * 3, cubeFaceWidth * 2, rubik.red)
        )
    }

    class RubikNavigationProposal private constructor (
        newRubik: Rubik<Matrix2D<MapPoint>>,
        x: Int,
        y: Int,
        direction: CrossDirection) : NavigationProposal(newRubik.currentFront(), x, y, direction ) {

        lateinit var rubik: Rubik<Matrix2D<MapPoint>>
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
        }

    }

    override fun proposalApplied(proposal: NavigationProposal) {
        proposal as RubikNavigationProposal
        rubik.set(proposal.rubik.currentFront(), proposal.rubik.currentUp())
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

            override fun blu(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                b = rawData.sub(0,len, len, len)
                return b
            }

            override fun orange(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                o = rawData.sub(len,len, len, len)
                return o
            }

            override fun green(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                g = rawData.sub(len * 2,len, len, len)
                return g
            }

            override fun yellow(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                y = rawData.sub(len * 2,len * 2, len, len)
                return y
            }

            override fun red(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                r = rawData.sub(len * 3,len * 2, len, len)
                return r
            }

            override fun navigate(
                faceFrom: Matrix2D<MapPoint>,
                faceTo: Matrix2D<MapPoint>,
                x: Int,
                y: Int
            ): CubeMap.CubeMapNavigation {
                return when(faceFrom) {
                    r -> {
                        if (faceTo == w) return CubeMap.CubeMapNavigation(x, endY(w), CrossDirection.U)
                        throw Exception("invalid $faceTo")
                    }
                    else -> throw Exception("invalid $faceFrom")
                }
            }

            val endY = { m: Matrix2D<MapPoint> -> m.ny - 1 }

        }
    }

}
