import com.toto_castaldi.common.CrossDirection
import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import com.toto_castaldi.common.structure.Rubik

class Aoc202222Part2(cubeMap: CubeMap) : Aoc202222() {
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
    
  /*  


    private fun move(steps: Int) {
        for (i in 0 until steps) {
                val proposal3D = nextSpot3D()
                if (proposal3D.newRubik.currentFront()[proposal3D.x, proposal3D.y] != MapPoint.WALL) {
                    x = proposal3D.x
                    y = proposal3D.y
                    rubik = proposal3D.newRubik
                }
            }
            markMap()
        }
    }

    data class Proposal3D (val x : Int, val y: Int, val newRubik : Rubik<Matrix2D<MapPoint>>, val newDirection: Direction)


    private fun nextSpot3D(): Proposal3D {
        val map = resolveMap()
        val newRubik = Rubik(rubik!!.blu, rubik!!.white, rubik!!.green, rubik!!.yellow, rubik!!.red, rubik!!.orange)
        newRubik.set(rubik!!.currentFront(), rubik!!.currentUp())
        return when (direction) {
            Direction.R -> {
                if (x + 1 == map.nx) {
                    Proposal3D.build(x, y, direction, rubik!!.currentFront(), newRubik.rotateUp().currentFront())
                } else {
                    Proposal3D.build(x +1 , y, rubik!!)
                }
            }
            Direction.L -> {
                if (x == 0) {
                    Proposal3D(map.nx - 1, y, newRubik.rotateUp(-1))
                } else {
                    Proposal3D(x -1 , y, rubik!!)
                }
            }
            Direction.D -> {
                if (y + 1 == map.ny) {
                    Proposal3D(x, 0, newRubik.rotateRight())
                } else {
                    Proposal3D(x  , y + 1, rubik!!)
                }
            }
            Direction.U -> {
                if (y == 0) {
                    Proposal3D(x, map.ny - 1, newRubik.rotateRight(-1))
                } else {
                    Proposal3D(x , 0, rubik!!)
                }
            }
        }
    }

*/
    interface CubeMap {
        fun white(rawData: Matrix2D<MapPoint>, edge: Int): Matrix2D<MapPoint>
        fun orange(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun green(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun blu(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun red(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
        fun yellow(rawData: Matrix2D<MapPoint>, edge : Int): Matrix2D<MapPoint>
    }

    companion object {
        val EXAMPLE_MAP: CubeMap = object : CubeMap {
            override fun white(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,0, len, len)
            }

            override fun blu(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(0,len, len, len)
            }

            override fun orange(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len,len, len, len)
            }

            override fun green(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,len, len, len)
            }

            override fun yellow(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 2,len * 2, len, len)
            }

            override fun red(rawData: Matrix2D<MapPoint>, len : Int): Matrix2D<MapPoint> {
                return rawData.sub(len * 3,len * 2, len, len)
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

    override fun changeCurrentMap(proposal: NavigationProposal) {
        TODO("Not yet implemented")
    }

    override fun nextSpot(x: Int, y: Int, direction: CrossDirection): NavigationProposal {
        TODO("Not yet implemented")
    }
}
