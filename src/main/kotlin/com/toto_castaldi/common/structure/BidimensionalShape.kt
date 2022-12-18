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
        fun base(shape: BidimensionalShape): BidimensionalShape {
            val newLines = mutableListOf<String>()
            var i = 0
            var l = shape.visualDescription[i]

            while (l.count { it == POINT_CHAR } != l.length && i < shape.visualDescription.size) {
                newLines.add(l)
                l = shape.visualDescription[++ i]
            }
            newLines.add(l)

            return  BidimensionalShape(newLines.toTypedArray())
        }

        val NULL_CHAR: Char = '.'
        val POINT_CHAR: Char = '#'
    }


}
