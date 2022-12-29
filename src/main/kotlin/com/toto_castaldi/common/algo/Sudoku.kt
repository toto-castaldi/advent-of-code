package com.toto_castaldi.common.algo

import com.toto_castaldi.common.structure.Coordinates
import com.toto_castaldi.common.structure.Matrix2D
import kotlin.math.floor

class Sudoku(private val inputGrid: List<String>) : BackTrackProblem<Matrix2D<Int>, Coordinates, Int>() {

    private val grid = Matrix2D<Int>(9,9, 0)

    init {
        for ((y, line) in inputGrid.withIndex()) {
            for ((x, v) in line.toCharArray().toList().withIndex()) {
                grid[x,y] = "$v".toInt()
            }
        }
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

    override fun isValid(nextMove: Coordinates, option : Int): Boolean {
        return valid(nextMove.x, nextMove.y, option) == 0
    }

    override fun stepOptions(v: Coordinates): Collection<Int> {
        return (1..9).toList()
    }

    override fun isNewStep(step: Coordinates): Boolean {
        return grid[step.x, step.y] == 0
    }

    override  fun stepIdentifiers() = sequence<Coordinates> {
        for (y in 0 until 9) {
            for (x in 0 until 9) {
                yield(Coordinates(x,y))
            }
        }
    }

    override fun currentResolution(): Matrix2D<Int> {
        return grid.bake()
    }

    override fun isComplete(workInProgress: Matrix2D<Int>): Boolean {
        return 0 !in workInProgress
    }

    override fun applyStep(nextMove: Coordinates, option: Int) {
        grid[nextMove.x, nextMove.y] = option
    }

    override fun revertStep(nextMove: Coordinates) {
        grid[nextMove.x, nextMove.y] = 0
    }

    override fun toString(): String {
        return grid.toString()
    }
}