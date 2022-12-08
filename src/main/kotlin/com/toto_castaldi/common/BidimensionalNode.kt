package com.toto_castaldi.common

import kotlin.math.abs

class BidimentionalNode<T>(var data: T) {

    private var neighbors: MutableMap<Coordinates, BidimentionalNode<T>> = mutableMapOf<Coordinates, BidimentionalNode<T>>()

    fun neighbors(): Collection<BidimentionalNode<T>> {
        return neighbors.values
    }

    fun neighbor(
        x: Int,
        y: Int,
        neighbor: BidimentionalNode<T>
    ): BidimentionalNode<T> {
        neighbors[Coordinates(x, y)] = neighbor
        neighbor.neighbors[Coordinates(-x, -y)] = this
        return this
    }

    fun resolve(
        stepX: Int,
        stepY: Int
    ): BidimentionalNode<T>? {
        return if (stepX == 0 && stepY == 0) {
            this
        } else if (abs(stepX) <= 1 && abs(stepY) <= 1) {
            neighbors[Coordinates(stepX, stepY)]
        } else {
            if (stepX < 0 && neighbors.containsKey(Coordinates(-1, 0))) {
                neighbors[Coordinates(-1, 0)]!!.resolve(stepX +1, stepY)
            } else if (stepX > 0 && neighbors.containsKey(Coordinates(1, 0))) {
                neighbors[Coordinates(1, 0)]!!.resolve(stepX -1, stepY)
            } else if (stepY < 0 && neighbors.containsKey(Coordinates(0, -1))) {
                neighbors[Coordinates(0, -1)]!!.resolve(stepX, stepY+1)
            } else {
                neighbors[Coordinates(0, 1)]!!.resolve(stepX, stepY-1)
            }
        }
    }

    override fun toString(): String {
        return data.toString()
    }

    companion object {
        fun <T> printNodes(
            tree: BidimentionalNode<T>
        ) {
            navigate(tree, { print("$it") }, { println() })
        }
        fun <T> navigate(
            node: BidimentionalNode<T>,
            action: (it: BidimentionalNode<T>) -> Unit,
            changeLine: () -> Unit
        ) {
            var navigation: BidimentionalNode<T>? = node
            var width = 0
            while (navigation != null) {
                action(navigation)
                if (navigation.resolve(1, 0) != null) {
                    width ++
                    navigation = navigation.resolve(1, 0)!!
                } else {
                    changeLine()
                    navigation = navigation.resolve(-width, 1)
                    width = 0
                }
            }
        }
        fun <T, R> build(matrix : List<List<T>>, builder : (T) -> R) : Pair<BidimentionalNode<R>?, Int> {
            var count = 0
            var pointer : BidimentionalNode<R>? = null
            for (y in 0 until matrix.size) {
                for (x in 0 until matrix[y].size) {
                    val node = BidimentionalNode(builder(matrix[y][x]))
                    count ++
                    if (x == 0) {
                        if (pointer != null) {
                            node.neighbor(0, -1, pointer.resolve(-matrix[y].size+1, 0)!!)
                        }
                    } else {
                        node.neighbor(-1, 0, pointer!!)
                        if (y > 0) {
                            val upLeft = pointer.resolve(0, -1)!!
                            val up = upLeft.resolve(1, 0)!!
                            node.neighbor(-1, -1, upLeft)
                            node.neighbor(0, -1, up)
                            up.neighbor(-1, 1, pointer)
                        }
                    }
                    pointer = node
                }
            }
            return Pair(pointer, count)
        }
    }
}