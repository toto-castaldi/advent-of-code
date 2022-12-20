package com.toto_castaldi.common.structure

class PrevNextNode<T>(val data: T) {

    var nextNode: PrevNextNode<T>? = null
    var prevNode: PrevNextNode<T>? = null

}
