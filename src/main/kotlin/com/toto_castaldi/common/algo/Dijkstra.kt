package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.FromTo
import com.toto_castaldi.common.structure.Graph

/**
 * https://www.youtube.com/watch?v=pVfj6mxhdMw
 */
class Dijkstra<T>(private val graph: Graph<T>) {

    private val weights = mutableMapOf<FromTo<T, T>, Int>()

    fun weight(fromNode: T, toNode: T, w: Int) {
        weights[FromTo(fromNode, toNode)] = w
        weights[FromTo(toNode, fromNode)] = w
    }

    fun shortestFrom(startingNode: T): Map<T, Int> {
        val unvisited = mutableSetOf<T>()
        val result = mutableMapOf<T, Int>()

        val computation = mutableMapOf<T, Pair<Int,T?>>()
        for (n in graph) {
            val t = n.data

            unvisited.add(t)

            if (t == startingNode) {
                computation[t] = Pair(0, null)
                result[t] = 0
            } else {
                computation[t] = Pair(Int.MAX_VALUE, null)
                result[t] = Int.MAX_VALUE
            }
        }

        var current = startingNode

        while (unvisited.size > 0) {

            var smallestDistance = Int.MAX_VALUE

            for (u in unvisited) {
                val p = computation[u]!!
                if (p.first < smallestDistance) {
                    smallestDistance = p.first
                    current = u
                }
            }

            for (unvisitedNeighbour in weights.filter { entry -> entry.key.from == current && entry.key.to in unvisited }) {
                val dest = unvisitedNeighbour.key.to
                val prev = computation[current]!!.first

                val dist = prev + weights[FromTo(current, dest)]!!

                if (dist < computation[dest]!!.first) {
                    computation[dest] = Pair(dist, current)
                    result[dest] = dist
                }
            }

            unvisited.remove(current)
        }

        return result
    }


}
