package com.toto_castaldi.common.algo

import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun algoTest () {
        val dijkstra = Dijkstra<String>()
        dijkstra + "A"
        dijkstra + "B"
        dijkstra + "C"
        dijkstra + "D"
        dijkstra + "E"

        dijkstra.weight("A", "B", 6)
        dijkstra.weight("A", "D", 1)
        dijkstra.weight("B", "D", 2)
        dijkstra.weight("D", "E", 1)
        dijkstra.weight("B", "E", 2)
        dijkstra.weight("B", "C", 5)
        dijkstra.weight("C", "E", 5)

        val shortest: Map<String, Int> = dijkstra.shortestFrom("A")

        assertEquals(5, shortest.size)
        assertEquals(0, shortest["A"])
        assertEquals(3, shortest["B"])
        assertEquals(7, shortest["C"])
        assertEquals(1, shortest["D"])
        assertEquals(2, shortest["E"])

    }
}