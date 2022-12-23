package com.toto_castaldi.common.structure

data class Cross<T> (val up: T, val right: T, val down: T, val left: T) {
    operator fun contains(f: T): Boolean {
        return f == up || f == right || f == down || f == left
    }

    fun clockWise () : Cross<T> {
        return Cross(left, up, right, down)
    }

    fun unClockWise () : Cross<T> {
        return Cross(right, down, left, up)
    }

    fun moveToDown(searchFor: T): Cross<T> {
        var c = this
        while (c.down != searchFor) c = c.clockWise()
        return c
    }

    fun moveToUp(searchFor: T): Cross<T> {
        var c = this
        while (c.up != searchFor) c = c.clockWise()
        return c
    }
}