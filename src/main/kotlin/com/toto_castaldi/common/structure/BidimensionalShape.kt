package com.toto_castaldi.common.structure

/**
 * Visual description example
.#.
###
.#.
 */
class BidimensionalShape(val visualDescription: Array<String>) {

    fun getHeight(): Int {
        return visualDescription.size
    }
    fun getWidth(): Int {
        return if (visualDescription.isNotEmpty()) visualDescription[0].length else 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as BidimensionalShape

        return this.visualDescription.contentEquals(other.visualDescription)
    }

    companion object {
        val NULL_CHAR: Char = '.'
        val POINT_CHAR: Char = '#'

        fun print(shape: BidimensionalShape) {
            for (line in shape.visualDescription) {
                println(line)
            }
        }
    }


}
