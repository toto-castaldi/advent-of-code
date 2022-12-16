package com.toto_castaldi.common.structure

data class Graph<T>(val data : T) {

    private val neighbors = mutableSetOf<Graph<T>>()
    fun addNeighbor(node: Graph<T>) {
        neighbors.add(node)
    }

}