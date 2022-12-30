package com.toto_castaldi.common.structure

class IntCoordinates(vararg var dims: Int) {

    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var t: Int = 0

    init {
        helpers()
    }

    private fun helpers() {
        x = if (dims.isNotEmpty()) dims[0] else x
        y = if (dims.size > 1) dims[1] else y
        z = if (dims.size > 2) dims[2] else z
        t = if (dims.size > 3) dims[3] else t
    }

    /**
     * change in place
     */
    fun move(vararg steps: Int): IntCoordinates {
        val res = mutableListOf<Int>()
        for (i in dims.indices) {
            res.add(dims[i] + steps[i])
        }
        dims = res.toIntArray()
        helpers()
        return this
    }

    fun clone(): IntCoordinates {
        return IntCoordinates(*dims.clone())
    }

    operator fun minus(toCoord: IntCoordinates): IntCoordinates {
        val res = mutableListOf<Int>()
        for (i in dims.indices) {
            res.add(dims[i] - toCoord.dims[i])
        }
        return IntCoordinates(*res.toIntArray())
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as IntCoordinates

        return other.dims contentEquals dims
    }

    override fun hashCode(): Int {
        return dims.contentHashCode()
    }

    override fun toString(): String {
        return dims.contentToString()
    }

}