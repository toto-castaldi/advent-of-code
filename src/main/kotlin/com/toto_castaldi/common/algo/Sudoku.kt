package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Matrix2D
import kotlin.math.floor

class Sudoku(private val inputGrid: List<String>) {
    val solutions = mutableListOf<Matrix2D<Int>>()
    private val grid = Matrix2D<Int>(9,9, 0)

    init {
        for ((y, line) in inputGrid.withIndex()) {
            for ((x, v) in line.toCharArray().toList().withIndex()) {
                grid[x,y] = "$v".toInt()
            }
        }
    }

    fun solve() {
        if (solutionFound()) {
            solutions.add(grid.bake())
        } else {
            //println(this)
            for (y in 0 until 9) {
                for (x in 0 until 9) {
                    if (grid[x, y] == 0) {
                        for (v in 1..9) {
                            if (valid(x, y, v) == 0) {
                                grid[x, y] = v
                                solve()
                                grid[x, y] = 0
                            }
                        }
                    }
                }
            }
        }
    }

    private fun solutionFound(): Boolean {
        var totalZeros = 0
        grid.forEach {line ->
            val zeros = line.filter { it == 0 }
            totalZeros += zeros.size
        }
        //println("found zeros ${totalZeros}")
        return totalZeros == 0
    }

    /**
     * -1 row
     * -2 column
     * -3 square
     *
     * 0 VALID
     */
    fun valid(x: Int, y: Int, v: Int): Int {
        if (v in grid.rowAt(y)) return -1
        if (v in grid.colAt(x)) return -2

        val x0 = floor(x.toDouble() / 3.0).toInt() * 3
        val y0 = floor(y.toDouble() / 3.0).toInt() * 3

        if (v in grid.sub(x0,y0,3,3)) return -3

        return 0
    }

    override fun toString(): String {
        return grid.toString()
    }
}