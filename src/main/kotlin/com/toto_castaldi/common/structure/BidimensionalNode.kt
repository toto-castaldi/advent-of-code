package com.toto_castaldi.common.structure

import kotlin.math.abs

data class NodeAndCounting<T>(val node: BidimentionalNode<T>?, val countNodes: Int)


class BidimentionalNode<T>(var data: T) {

    private var neighbors: MutableMap<IntCoordinates, BidimentionalNode<T>> = mutableMapOf<IntCoordinates, BidimentionalNode<T>>()

    fun neighbors(): Collection<BidimentionalNode<T>> {
        return neighbors.values
    }

    fun neighbor(
        x: Int,
        y: Int,
        neighbor: BidimentionalNode<T>
    ): BidimentionalNode<T> {
        neighbors[IntCoordinates(x, y)] = neighbor
        neighbor.neighbors[IntCoordinates(-x, -y)] = this
        return this
    }

    fun removeNeighbor(x: Int, y: Int): BidimentionalNode<T> {
        neighbors[IntCoordinates(x, y)]?.neighbors?.remove(IntCoordinates(-x, -y))
        neighbors.remove(IntCoordinates(x, y))
        return this
    }
    /**
     * moving from here to a distant node
     */
    fun resolve(
        coordinates: IntCoordinates
    ): BidimentionalNode<T>? {
        return resolve(coordinates.x, coordinates.y)
    }

    /**
     * moving from here to a distant node
     */
    fun resolve(
        dirX: Int,
        dirY: Int
    ): BidimentionalNode<T>? {
        return if (dirX == 0 && dirY == 0) {
            this
        } else if (abs(dirX) <= 1 && abs(dirY) <= 1) {
            neighbors[IntCoordinates(dirX, dirY)]
        } else {
            if (dirX < 0 && neighbors.containsKey(IntCoordinates(-1, 0))) {
                neighbors[IntCoordinates(-1, 0)]!!.resolve(dirX +1, dirY)
            } else if (dirX > 0 && neighbors.containsKey(IntCoordinates(1, 0))) {
                neighbors[IntCoordinates(1, 0)]!!.resolve(dirX -1, dirY)
            } else if (dirY < 0 && neighbors.containsKey(IntCoordinates(0, -1))) {
                neighbors[IntCoordinates(0, -1)]!!.resolve(dirX, dirY+1)
            } else {
                neighbors[IntCoordinates(0, 1)]!!.resolve(dirX, dirY-1)
            }
        }
    }

    override fun toString(): String {
        return data.toString()
    }

    fun topLeft(): BidimentionalNode<T> {
        //top
        var pointer = this
        var nextTop = pointer.resolve(0, -1)
        while (nextTop != null) {
            pointer = nextTop
            nextTop = pointer.resolve(0, -1)
        }
        //left
        var nextLeft = pointer.resolve(-1, 0)
        while (nextLeft != null) {
            pointer = nextLeft
            nextLeft = pointer.resolve(-1, 0)
        }
        return pointer
    }

    fun onTheEdge(): Boolean {
        return neighbors.size < 8
    }

    fun u(): BidimentionalNode<T>? { return resolve(0,-1) }
    fun d(): BidimentionalNode<T>? { return resolve(0,1) }
    fun l(): BidimentionalNode<T>? { return resolve(-1,0) }
    fun r(): BidimentionalNode<T>? { return resolve(1,0) }
    fun ur(): BidimentionalNode<T>? { return resolve(1,-1) }
    fun dr(): BidimentionalNode<T>? { return resolve(1,1) }
    fun dl(): BidimentionalNode<T>? { return resolve(-1,1) }
    fun ul(): BidimentionalNode<T>? { return resolve(-1,-1) }


    companion object {

        fun <T> printNodes(
            tree: BidimentionalNode<T>,
            format: (it: T) -> String = { it.toString() }
        ) {
            navigate(tree, { print(format(it.data)) }, { println() })
        }

        fun <T> navigate(
            node: BidimentionalNode<T>,
            action: (it: BidimentionalNode<T>) -> Unit
        ) {
            return navigate(node, action) {}
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

        /**
         * build and return the last node in the grid
         */
        fun <T, R> build(matrix : List<List<T>>, builder : (Int, Int, T) -> R) : NodeAndCounting<R> {
            var count = 0
            var pointer : BidimentionalNode<R>? = null
            for (y in 0 until matrix.size) {
                for (x in 0 until matrix[y].size) {
                    val node = BidimentionalNode(builder(x,y,matrix[y][x]))
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
            return NodeAndCounting(pointer, count)
        }
    }
}