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

    companion object {
        val NULL_CHAR: Char = '.'
        val POINT_CHAR: Char = '#'
    }


}
