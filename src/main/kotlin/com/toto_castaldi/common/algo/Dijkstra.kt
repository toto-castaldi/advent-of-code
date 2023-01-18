package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.FromTo
import com.toto_castaldi.common.structure.Graph

/**
 * https://www.youtube.com/watch?v=pVfj6mxhdMw
 */
class Dijkstra<T>(private val graph: Graph<T>) {

    private val weights = mutableMapOf<FromTo<T, T>, Int>()

    fun arc(fromNode: T, toNode: T, w: Int) {
        weights[FromTo(fromNode, toNode)] = w
    }

    fun shortestFrom(startingNode: T): Map<T, Int?> {
        val unvisited = mutableSetOf<T>()

        val computation = mutableMapOf<T, DistancePrevious<T>>()
        for (n in graph) {
            val t = n.data

            unvisited.add(t)

            if (t == startingNode) {
                computation[t] = DistancePrevious.zero()
            } else {
                computation[t] = DistancePrevious.infinite()
            }
        }

        while (unvisited.size > 0) {

            val current = unvisited.minBy { node -> computation[node]!!.shortestDistance }

            if (!computation[current]!!.isInfinite()) {
                for (unvisitedNeighbour in weights.filter { entry -> entry.key.from == current && entry.key.to in unvisited }) {
                    val dest = unvisitedNeighbour.key.to
                    val prev = computation[current]!!.shortestDistance

                    val dist = prev + weights[FromTo(current, dest)]!!

                    if (dist < computation[dest]!!.shortestDistance) {
                        computation[dest] = DistancePrevious(dist, current)
                    }
                }
            }

            unvisited.remove(current)
        }

        return computation.mapValues { entry -> if (entry.value.isInfinite()) null else entry.value.shortestDistance}
    }

    data class DistancePrevious<T>(val shortestDistance : Int, val previous: T?) {

        val isInfinite = { shortestDistance == Int.MAX_VALUE }

        override fun toString(): String {
            return "$shortestDistance from $previous"
        }
        companion object {
            fun <T> zero(): DistancePrevious<T> {
                return DistancePrevious(0, null)
            }

            fun <T> infinite(): DistancePrevious<T> {
                return DistancePrevious(Int.MAX_VALUE, null)
            }
        }
    }


}
