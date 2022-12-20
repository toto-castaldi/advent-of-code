package com.toto_castaldi.common.structure

class PrevNextNode<T>(val data: T) {
    fun next(pos: Int): PrevNextNode<T>? {
        if (pos == 0) return this
        if (pos > 0) {
            var result: PrevNextNode<T>? = this
            var t = 0
            while (result != null && t < pos) {
                result = result.nextNode
                t++
            }
            return result
        } else {
            return prev(-pos + 1)
        }
    }

    fun prev(pos: Int): PrevNextNode<T>? {
        if (pos == 0) return this
        if (pos > 0) {
            var result: PrevNextNode<T>? = this
            var t = 0
            while (result != null && t < pos) {
                result = result.prevNode
                t++
            }
            return result
        } else {
            return next(-pos + 1)
        }
    }

    var nextNode: PrevNextNode<T>? = null
    var prevNode: PrevNextNode<T>? = null

    override fun toString(): String {
        var result = "(${data.toString()})"
        if (prevNode != null) result = prevNode!!.data.toString() + " <- " + result
        if (nextNode != null) result += " -> " + nextNode!!.data.toString()
        return result
    }

}
