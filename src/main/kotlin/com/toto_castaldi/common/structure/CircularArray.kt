package com.toto_castaldi.common.structure

class CircularArray<T> {

    var firstNode : PrevNextNode<T>? = null
    var addingNode : PrevNextNode<T>? = null
    var size = 0

    fun add(element: T): PrevNextNode<T> {
        if (firstNode == null) {
            firstNode = PrevNextNode(element)
            addingNode = firstNode
        } else {
            val nextNode = PrevNextNode(element)

            addingNode?.setNext(nextNode)

            nextNode.setPrev(addingNode!!)
            nextNode.setNext(firstNode!!)

            firstNode?.setPrev(nextNode)

            addingNode = nextNode
        }
        size ++
        return addingNode!!
    }

    fun moveRigth(element: PrevNextNode<T>, steps: Int) {
        if (steps % size != 0) {
            val op = element.prev()
            val on = element.next()
            val np = element.next(steps % size)
            val nn = np?.next()

            op?.setNext(on)
            on?.setPrev(op)

            np?.setNext(element)
            element.setPrev(np)

            nn?.setPrev(element)
            element.setNext(nn)
        }
    }

    fun findBy(testData: (it : T) -> Boolean): PrevNextNode<T>? {
        var p : PrevNextNode<T>?= firstNode
        while (p!= null && p.next() != firstNode) {
            if (testData(p.data)) return p
            p = p.next()
        }
        if (p!= null && testData(p.data)) return p
        return null
    }

    fun values(printFrom : PrevNextNode<T>): List<T> {
        val result = mutableListOf<T>()
        var p: PrevNextNode<T>? = printFrom
        while (p!= null && p.next() != printFrom) {
            result.add(p.data)
            p = p.next()
        }
        result.add(p!!.data)
        return result
    }

}
