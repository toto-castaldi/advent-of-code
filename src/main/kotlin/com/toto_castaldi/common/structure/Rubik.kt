package com.toto_castaldi.common.structure

//"BLU", "WHITE", "GREEN", "YELLOW", "RED", "ORANGE"
// 0         1      2         3       4         5
class Rubik<T>(vararg val faces : T) {
    var f = FACE.Y
    var u = FACE.G
    var upper = mapOf(
        FACE.B to listOf(FACE.R, FACE.Y, FACE.O, FACE.W),
        FACE.W to listOf(FACE.G, FACE.R, FACE.B, FACE.O),
        FACE.G to listOf(FACE.O, FACE.Y, FACE.R, FACE.W),
        FACE.Y to listOf(FACE.G, FACE.O, FACE.B, FACE.R),
        FACE.R to listOf(FACE.W, FACE.G, FACE.Y, FACE.B),
        FACE.O to listOf(FACE.Y, FACE.G, FACE.W, FACE.B)
    )

    init {
        if (faces.size != 6) throw Exception("Provide 6 different faces instead $faces")
    }

    enum class FACE {
        B, W, G, Y, R, O
    }

    fun set(iF: T, iU : T) {
        f = FACE.values()[faces.indexOf(iF)]
        u = FACE.values()[faces.indexOf(iU)]
        checkFU()
    }

    private fun checkFU() {
        if (u !in upper[f]!!) throw Exception("$u can't be on top of $f")
    }

    fun currentFace(): T {
        return faces[f.ordinal]
    }

    fun currentUp(): T {
        return faces[u.ordinal]
    }

    fun rotateY(dir : Int = 1) {
        for (i in 0 until dir) {
            if (dir > 0) {
                rotateX(1)
                u = prev(upper[f]!!, u)
                rotateX(-1)
            } else {
                rotateX(+1)
                u = next(upper[f]!!, u)
                rotateX(-1)
            }
        }
    }

    fun rotateX(dir : Int = 1) {
        for (i in 0 until dir) {
            if (dir > 0) {
                f = next(upper[u]!!, f)
            } else {
                f = prev(upper[u]!!, f)
            }
        }
    }




    private fun next(theFaceList: List<FACE>, theFace: FACE): FACE {
        val i = theFaceList.indexOf(theFace) + 1
        return if (i < theFaceList.size) theFaceList[i] else theFaceList.first()
    }

    private fun prev(theFaceList: List<FACE>, theFace: FACE): FACE {
        val i = theFaceList.indexOf(theFace) - 1
        return if (i > 0) theFaceList[i] else theFaceList.last()
    }


}