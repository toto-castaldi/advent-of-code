package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.BidimentionalNode

/**
 * https://en.wikipedia.org/wiki/Hill_climbing
 *
 * Pseudocode
 * algorithm Discrete Space Hill Climbing is
 *     currentNode := startNode
 *     loop do
 *         L := NEIGHBORS(currentNode)
 *         nextEval := −INF
 *         nextNode := NULL
 *         for all x in L do
 *             if EVAL(x) > nextEval then
 *                 nextNode := x
 *                 nextEval := EVAL(x)
 *         if nextEval ≤ EVAL(currentNode) then
 *             // Return current node since no better neighbors exist
 *             return currentNode
 *         currentNode := nextNode
 *
 */
class DiscreteHillClimbing<T>(private val startNode: BidimentionalNode<T>) {

    fun compute(destination: BidimentionalNode<T>, EVAL : (BidimentionalNode<T>) -> Int): List<BidimentionalNode<T>> {
        val result = mutableListOf<BidimentionalNode<T>>()
        var currenNode = startNode
        while (currenNode != destination) {
            val L = currenNode.neighbors()
            var nextEval = Int.MIN_VALUE
            var nextNode : BidimentionalNode<T>? = null
            for (x in L) {
                if (EVAL(x) > nextEval) {
                    nextNode = x
                    nextEval = EVAL(x)
                }
            }
            if (nextEval <= EVAL(currenNode)) {
                result.add(currenNode)
            }
            currenNode = nextNode!!
        }
        return result
    }

}
