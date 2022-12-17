package com.toto_castaldi.common.structure

/**
 * Visual description example
.#.
###
.#.
 */
class BidimensionalShape(visualDescription: Array<String>) {
    fun containingSquare(): PlacedRectangle {
        TODO()
    }

    val height: Int
    val width: Int

    init {
        height = visualDescription.size
        width = if (visualDescription.isNotEmpty()) visualDescription[0].length else 0
    }

    companion object {
        val EMPTY: BidimensionalShape = BidimensionalShape(emptyArray())
    }

}
