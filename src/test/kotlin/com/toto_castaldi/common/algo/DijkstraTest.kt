package com.toto_castaldi.common.algo

import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTest {

    @Test
    fun testSimple() {
        val dijkstra = Dijkstra<Int>()
            .arc(0,1)
            .arc(0,2)
            .arc(1,2)
            .arc(2,3)
        assertEquals(listOf(0,1), dijkstra.shortestPath(0,1))
        assertEquals(listOf(0,2,3) , dijkstra.shortestPath(0,3))
    }

    @Test
    fun testLine() {
        val dijkstra = Dijkstra<Int>()
        for (i in 0 until 99) {
            dijkstra.arc(i, i + 1)
        }
        assertEquals(51, dijkstra.shortestPath(0,50).size)
        assertEquals(100, dijkstra.shortestPath(0,99).size)
        assertEquals(listOf(50,51) , dijkstra.shortestPath(50,51))
        assertEquals(listOf(0) , dijkstra.shortestPath(50,0))
    }

    @Test
    fun testU3for3() {
        val dijkstra = Dijkstra<Int>()
            .arc(0,3)
            .arc(3,6)
            .arc(6,7)
            .arc(7,8)
            .arc(8,5)
            .arc(5,2)
        assertEquals(listOf(0, 3, 6, 7, 8, 5, 2) , dijkstra.shortestPath(0,2))
    }
}