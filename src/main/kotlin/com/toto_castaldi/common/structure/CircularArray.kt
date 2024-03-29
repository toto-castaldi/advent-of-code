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

    fun moveRigth(element: PrevNextNode<T>, steps: Long) {
        if (steps  != 0L) {
            val count = steps % (size - 1)
            if (count > 0) {
                for (i in 0 until count) {
                    moveStep(element, 1)
                }
            } else {
                for (i in 0 until -count) {
                    moveStep(element, -1)
                }
            }
        }
    }

    fun moveStep(element: PrevNextNode<T>, dir : Int) {
        val op = element.prev()
        val on = element.next()
        val np = element.next(dir)
        val nn = np?.next()

        if (np == element || nn == element) {
            throw Exception("next produce a wrong element !!! ")
        }

        op?.setNext(on)
        on?.setPrev(op)

        np?.setNext(element)
        element.setPrev(np)

        nn?.setPrev(element)
        element.setNext(nn)

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

    fun nodes() = sequence<T> {
        var p: PrevNextNode<T>? = firstNode
        while (p!= null && p.next() != firstNode) {
            yield(p.data)
            p = p.next()
        }
        yield(p!!.data)
    }


}
