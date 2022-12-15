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
class DiscreteHillClimbing<T>(private val hillStartingNode: BidimentionalNode<T>) {

    private fun discreteSpaceHillClimbing(node: BidimentionalNode<T>, nodeScore : (BidimentionalNode<T>) -> Double): BidimentionalNode<T> {
        var currentScore = nodeScore(node)
        var result = node
        for (n in node.neighbors()) {
            val nEval = nodeScore(n)
            if (nEval > currentScore) {
                currentScore = nEval
                result = n
            }
        }
        //println("propose ${result} for ${node}")
        return result
    }

    fun compute(destination: BidimentionalNode<T>, nodeScore : (BidimentionalNode<T>) -> Double): List<BidimentionalNode<T>> {
        val result = mutableListOf<BidimentionalNode<T>>()

        result.add(hillStartingNode)

        var pointer = hillStartingNode
        while (pointer != destination) {
            var next = discreteSpaceHillClimbing(pointer, nodeScore)
            result.add(next)
            pointer = next
        }

        return result
    }

}
