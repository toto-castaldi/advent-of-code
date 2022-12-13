package com.toto_castaldi.common.algo

import com.toto_castaldi.common.Graph

class Dijkstra<T>() {

    private lateinit var graph: Graph<T>
    private val weights = mutableMapOf<Pair<T,T>, Int>()

    fun arc(fromNode: T, toNode: T, weight: Int): Dijkstra<T> {
        weights[Pair(fromNode, toNode)] = weight
        graph = Graph<T>(weights)
        return this
    }

    fun arc(fromNode: T, toNode: T): Dijkstra<T> {
        return arc(fromNode, toNode, 1)
    }

    fun shortestPath(start: T, end: T): List<T> {
        val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

        /*
         * delta represents the length of the shortest distance paths
         * from start to v, for v in vertices.
         *
         * The values are initialized to infinity, as we'll be getting the key with the min value
         */
        val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
        delta[start] = 0

        val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

        while (S != graph.vertices) {
            // let v be the closest vertex that has not yet been visited
            val v: T = delta
                .filter { !S.contains(it.key) }
                .minBy { it.value }!!
                .key

            graph.edges.getValue(v).minus(S).forEach { neighbor ->
                val k = Pair(v, neighbor)
                val weight = if (graph.weights.containsKey(k)) graph.weights.getValue(k) else Int.MAX_VALUE
                val newPath = delta.getValue(v) + weight

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            S.add(v)
        }

        val shortestPathTree: Map<T, T?> = previous.toMap()

        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

}
