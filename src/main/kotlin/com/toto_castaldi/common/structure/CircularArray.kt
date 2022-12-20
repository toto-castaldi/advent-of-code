package com.toto_castaldi.common.structure

import kotlin.math.abs

class CircularArray<T> : ArrayList<T>() {

    fun moveRight(index: Int, steps: Int): Int {
        if (steps > 0) {
            val movedValue = this[index]
            for (movingIndex in index + 1..(index + steps)) {
                this[movingIndex - 1] = this[movingIndex]
            }
            this[index + steps] = movedValue
            return index + steps
        } else {
            return moveLeft(index, -steps)
        }
    }

    fun moveLeft(index: Int, steps: Int): Int {
        if (steps > 0) {
            val movedValue = this[index]
            for (movingIndex in index downTo (index - steps + 1)) {
                this[movingIndex] = this[movingIndex - 1]
            }
            this[index - steps] = movedValue
            return index - steps
        } else {
            return moveRight(index, -steps)
        }
    }

    override fun set(index: Int, element: T): T {
        if (index < 0) {
            if (size + index > 0) {
                return super.set(size + index, element)
            } else {
                return set(index % size, element)
            }
        } else {
            return super.set(index % size, element)
        }
    }

    override fun get(index: Int): T {
        if (index < 0) {
            if (size + index > 0) {
                return super.get(size + index)
            } else {
                return get(index % size)
            }
        } else {
            return super.get(index % size)
        }
    }

}
