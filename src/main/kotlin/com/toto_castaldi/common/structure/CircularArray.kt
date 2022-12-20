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

            addingNode?.nextNode = nextNode

            nextNode.prevNode = addingNode!!
            nextNode.nextNode = firstNode!!

            firstNode?.prevNode = nextNode

            addingNode = nextNode
        }
        return addingNode!!
    }

    fun moveRigth(element: PrevNextNode<T>, steps: Int) {
        if (steps != 0) {
            val op = element.prevNode
            val on = element.nextNode
            val np = element.next(steps)
            val nn = np?.nextNode

            op?.nextNode = on
            on?.prevNode = op

            np?.nextNode = element
            element.prevNode = np

            nn?.prevNode = element
            element.nextNode = nn
        }
    }

    fun findBy(testData: (it : T) -> Boolean): PrevNextNode<T>? {
        var p : PrevNextNode<T>?= firstNode
        while (p!= null && p.nextNode != firstNode) {
            if (testData(p.data)) return p
            p = p.nextNode
        }
        if (p!= null && testData(p.data)) return p
        return null
    }

    fun values(printFrom : PrevNextNode<T>): List<T> {
        val result = mutableListOf<T>()
        var p: PrevNextNode<T>? = printFrom
        while (p!= null && p.nextNode != printFrom) {
            result.add(p.data)
            p = p.nextNode
        }
        result.add(p!!.data)
        return result
    }

}
