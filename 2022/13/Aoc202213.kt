import java.io.File

class Aoc202213 {

    private val pairs = mutableListOf<PacketPair>()

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

    fun getPair(index: Int): PacketPair {
        return pairs[index - 1]
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

        override fun toString(): String {
            return leftPacket.toString() + "\n" + rigthPacket.toString()
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

            override fun toString(): String {
                var result = "["
                for ((i, v) in values.withIndex()) {
                    if (v.first != null) {
                        result += v.first!!
                    } else {
                        result += v.second!!.toString()
                    }
                    if (i < values.size - 1 ) {
                        result += ","
                    }
                }
                result += "]"
                return result
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

    companion object {
        fun build(fileName: String): Aoc202213 {
            return build(File(fileName).readLines())
        }
        fun build(lines: List<String>): Aoc202213 {
            val chunks = lines.chunked(3)
            val result = Aoc202213()


            for (chunk in chunks) {
                val first = chunk[0]
                val second = chunk[1]

                val packetPair = result.addPair()

                fun addAndClean(strDigits: String, packet: PacketPair.Packet): String {
                    var result = strDigits
                    if (result.isNotEmpty()) {
                        packet.add(result.toInt())
                        result = ""
                    }
                    return result
                }

                fun parsePacket(inputPacket: PacketPair.Packet, line: String) {
                    var packet = inputPacket
                    var index = 1
                    var strDigits = ""
                    while (index < line.length - 1) {
                        if (line[index].isDigit()) {
                            strDigits += line[index]
                        } else if (line[index] == ',') {
                            strDigits = addAndClean(strDigits, packet)
                        } else if (line[index] == '[') {
                            packet = packet.push()
                        } else { //]
                            strDigits = addAndClean(strDigits, packet)
                            packet = packet.pop()
                        }
                        index ++
                    }
                    addAndClean(strDigits, packet)
                }

                parsePacket(packetPair.left(), first)
                parsePacket(packetPair.rigth(), second)
            }
            return result
        }


    }

}
