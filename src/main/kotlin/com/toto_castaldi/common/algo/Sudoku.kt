package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Matrix2D
import kotlin.math.floor

class Sudoku(val inputGrid: List<String>) {
    private val grid = Matrix2D<Int>(9,9, 0)

    init {
        for ((y, line) in inputGrid.withIndex()) {
            for ((x, v) in line.toCharArray().toList().withIndex()) {
                grid[x,y] = "$v".toInt()
            }
        }
    }

    fun solve() {
        for (y in 0 until 9) {
            for (x in 0 until 9) {
                if (grid[x,y] == 0) {
                    for (v in 1..9) {
                        if (valid(x,y,v)) grid[x,y] = v
                        solve()
                        grid[x,y] = 0
                    }
                    return
                }
            }
        }
    }

    fun valid(x: Int, y: Int, v: Int): Boolean {
        for (i in 0 until 9) if (grid[x, i] == v && i != y) return false //column
        for (i in 0 until 9) if (grid[i, y] == v && i != y) return false //row
        val x0 = floor(x.toDouble() / 3.0).toInt() * 3
        val y0 = floor(y.toDouble() / 3.0).toInt() * 3
        println("$x0 $y0")
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                println(grid[y0 + i, x0 + j])
                if (grid[y0 + i, x0 + j] == v) return false
            }
        }
        return true
    }

    override fun toString(): String {
        return grid.toString()
    }
}