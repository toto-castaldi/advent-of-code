import java.io.File

class Aoc202213 {

    val pairs = mutableListOf<PacketPair>()

    fun run(fileName: String) {
        val chunks = File(fileName).readLines().chunked(3)
        for (chunk in chunks) {
            val first = chunk[0]
            val second = chunk[1]

        }
    }

    fun addPair(): PacketPair {
        val result = PacketPair()
        pairs.add(result)
        return result
    }

    fun correctOrderIndexes(): List<Int> {
        val result = mutableListOf<Int>()
        for ((index, pp) in pairs.withIndex()) {
            if (pp.isInRightOrder()) {
                result.add(index + 1)
            }
        }
        return result
    }

    class PacketPair() {

        private var leftPacket = Packet()
        private var rigthPacket = Packet()

        fun left(): Packet {
            return leftPacket
        }

        fun rigth(): Packet{
            return rigthPacket
        }


        fun isInRightOrder(): Boolean {
            for ((lIndex, lValue) in leftPacket.values.withIndex()) {
                val rValue = if (rigthPacket.values.size > lIndex) rigthPacket.values[lIndex] else null
                if (rValue == null) {
                    return false //Right side ran out of items, so inputs are not in the right order
                } else {
                    if (lValue.first != null && rValue.first != null) { //both values are integers
                        if (lValue.first!! < rValue!!.first!!) {
                            return true
                        } else if (lValue.first!! > rValue!!.first!!) {
                            return false
                        }
                    } else if (lValue.second != null && rValue.second != null) { //If both values are lists
                        return fromFather(lValue.second!!, rValue.second!!).isInRightOrder()
                    } else {
                        if (lValue.second != null) { //first list, second number
                            return fromFather(lValue.second!!, Packet().add(rValue.first!!)).isInRightOrder()
                        } else { //first number, second list
                            return fromFather(Packet().add(lValue.first!!), rValue.second!!).isInRightOrder()
                        }
                    }
                }
            }
            return true //Left side ran out of items, so inputs are in the right order
        }

        class Packet {
            private var father: Aoc202213.PacketPair.Packet? = null
            internal val values = mutableListOf<Pair<Int?, Packet?>>()
            fun add(value: Int): Packet {
                values.add(Pair(value, null))
                return this
            }

            fun push(): Packet {
                val child = Packet()
                child.father = this
                values.add(Pair(null, child))
                return child
            }

            fun pop(): Packet {
                return father!!
            }

        }

        companion object {
            fun fromFather(lP: Packet, rP: Packet): PacketPair {
                val result = PacketPair()
                result.leftPacket = lP
                result.rigthPacket = rP
                return result
            }
        }

    }

}
