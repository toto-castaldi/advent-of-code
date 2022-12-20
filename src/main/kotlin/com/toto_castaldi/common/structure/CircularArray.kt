package com.toto_castaldi.common.structure

class CircularArray<T> {

    var firstNode : PrevNextNode<T>? = null
    var addingNode : PrevNextNode<T>? = null

    fun add(element: T): PrevNextNode<T> {
        if (firstNode == null) {
            firstNode = PrevNextNode(element)
            addingNode = firstNode
        } else {
            val nextNode = PrevNextNode(element)
            addingNode!!.nextNode = nextNode
            nextNode.prevNode = addingNode!!
            nextNode.nextNode = firstNode!!

            addingNode = nextNode
        }
        return addingNode!!
    }

    operator fun get(steps: Int): T {
        var current = firstNode
        for (i in 0 until steps) {
            current = current!!.nextNode
        }
        return current!!.data
    }

    fun moveRigth(elment: PrevNextNode<T>, steps: Int): Int {
        TODO("Not yet implemented")
    }

    fun findBy(testData: (it : T) -> Boolean): PrevNextNode<T>? {
        var p : PrevNextNode<T>?= firstNode
        while (p!= null && p.nextNode != firstNode) {
            if (p!= null && testData(p!!.data)) return p
            if (p == null) p = firstNode else p = p.nextNode
        }
        if (p!= null && testData(p!!.data)) return p
        return null
    }

    fun values(): List<T> {
        val result = mutableListOf<T>()
        var p = firstNode
        while (p!= null && p.nextNode != firstNode) {
            result.add(p.data)
            p = p.nextNode
        }
        result.add(p!!.data)
        return result
    }


}
