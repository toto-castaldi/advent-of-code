package com.toto_castaldi.common.structure

class PrevNextNode<T>(val data: T) {
    fun next(pos: Int = 1): PrevNextNode<T>? {
        if (pos == 0) return this
        if (pos > 0) {
            var result: PrevNextNode<T>? = this
            var t = 0
            while (result != null && t < pos) {
                result = result.nNode
                t++
            }
            return result
        } else {
            return prev(-pos + 1)
        }
    }

    fun prev(pos: Int = 1): PrevNextNode<T>? {
        if (pos == 0) return this
        if (pos > 0) {
            var result: PrevNextNode<T>? = this
            var t = 0
            while (result != null && t < pos) {
                result = result.pNode
                t++
            }
            return result
        } else {
            return next(-pos + 1)
        }
    }

    private var nNode: PrevNextNode<T>? = null
    private var pNode: PrevNextNode<T>? = null

    override fun toString(): String {
        var result = "(${data.toString()})"
        if (pNode != null) result = pNode!!.data.toString() + " <- " + result
        if (nNode != null) result += " -> " + nNode!!.data.toString()
        return result
    }

    fun setNext(node: PrevNextNode<T>?) {
        if (node == this) throw Exception("circular error !!! $this -> $node")
        nNode = node
    }

    fun setPrev(node: PrevNextNode<T>?) {
        if (node == this) throw Exception("circular error !!! $this <- $node")
        pNode = node
    }

}
