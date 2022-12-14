package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Coordinates
import java.util.*

class Lee(val mat: Array<IntArray>) {

    // queue node used in BFS
    internal class Node(
        // (x, y) represents matrix cell coordinates
        // dist represent its minimum distance from the source
        var x: Int, var y: Int, var dist: Int,
    )


    private val row = intArrayOf(-1, 0, 0, 1)
    private val col = intArrayOf(0, -1, 1, 0)

    fun bfs(fromCoord: Coordinates, toCoord: Coordinates): Int {
        val M = mat.size
        val N = mat[0].size
        fun isValid(
            mat: Array<IntArray>, visited: Array<BooleanArray>,
            row: Int, col: Int,
        ): Boolean {
            return row >= 0 && row < M && col >= 0 && col < N && mat[row][col] == 1 && !visited[row][col]
        }

        var i = fromCoord.x
        var j = fromCoord.y
        val x = toCoord.x
        val y = toCoord.y

        val visited = Array(M) {
            BooleanArray(
                N
            )
        }

        // create an empty queue
        val q: Queue<Node> = ArrayDeque()

        // mark source cell as visited and enqueue the source node
        visited[i][j] = true
        q.add(Node(i, j, 0))

        // stores length of longest path from source to destination
        var min_dist = Int.MAX_VALUE

        // run till queue is not empty
        while (!q.isEmpty()) {
            // pop front node from queue and process it
            val node = q.poll()

            // (i, j) represents current cell and dist stores its
            // minimum distance from the source
            i = node.x
            j = node.y
            val dist = node.dist

            // if destination is found, update min_dist and stop
            if (i == x && j == y) {
                min_dist = dist
                break
            }

            // check for all 4 possible movements from current cell
            // and enqueue each valid movement
            for (k in 0..3) {
                // check if it is possible to go to position
                // (i + row[k], j + col[k]) from current position
                if (isValid(mat, visited, i + row[k], j + col[k])) {
                    // mark next cell as visited and enqueue it
                    visited[i + row[k]][j + col[k]] = true
                    q.add(Node(i + row[k], j + col[k], dist + 1))
                }
            }
        }
        if (min_dist != Int.MAX_VALUE) {
            return min_dist
        } else {
            return -1
        }
    }

}

fun main() {
    val mat = arrayOf(
        intArrayOf(1, 1, 1, 1, 1, 0, 0, 1, 1, 1),
        intArrayOf(0, 1, 1, 1, 1, 1, 0, 1, 0, 1),
        intArrayOf(0, 0, 1, 0, 1, 1, 1, 0, 0, 1),
        intArrayOf(1, 0, 1, 1, 1, 0, 1, 1, 0, 1),
        intArrayOf(0, 0, 0, 1, 0, 0, 0, 1, 0, 1),
        intArrayOf(1, 0, 1, 1, 1, 0, 0, 1, 1, 0),
        intArrayOf(0, 0, 0, 0, 1, 0, 0, 1, 0, 1),
        intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 0, 0),
        intArrayOf(1, 1, 1, 1, 1, 0, 0, 1, 1, 1),
        intArrayOf(0, 0, 1, 0, 0, 1, 1, 0, 0, 1)
    )
    val lee = Lee(mat)
    val bfs = lee.bfs(Coordinates(0,0), Coordinates(7,5))
    println(bfs)
    assert(bfs == 12)

}