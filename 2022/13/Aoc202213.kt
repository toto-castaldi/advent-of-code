import java.io.File

class Aoc202213 {

    val pairs = mutableListOf<PacketPair>()

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
            return leftPacket < rigthPacket
        }

        class IntOrPacket {
            fun isInt(): Boolean {
                return valueInt != null
            }

            fun toInt(): Int {
                return valueInt!!
            }

            fun toPacket(): Packet {
                return valuePacket!!
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other?.javaClass != javaClass) return false

                other as IntOrPacket

                if (isInt() && other.isInt()) {
                    return valueInt!! == other.valueInt!!
                } else if (!isInt() && !other.isInt()) {
                    return valuePacket!! == other.valuePacket!!
                }

                return false
            }

            operator fun compareTo(other: IntOrPacket): Int {
                if (isInt() && other.isInt()) {
                    return toInt().compareTo(other.toInt())
                } else if (!isInt() && !other.isInt()) {
                    val c = toPacket().compareTo(other.toPacket())
                    if (c != 0) {
                        return c
                    }
                } else if (isInt()) {
                    return build(Packet().add(toInt())).compareTo(other)
                } else {
                    return this.compareTo(build(Packet().add(other.toInt())))
                }
                return 0
            }

            override fun hashCode(): Int {
                var result = valueInt ?: 0
                result = 31 * result + (valuePacket?.hashCode() ?: 0)
                return result
            }

            private var valueInt : Int? = null
            private var valuePacket : Packet? = null

            companion object {
                fun build(value: Int): IntOrPacket {
                    val result = IntOrPacket()
                    result.valueInt = value
                    return result
                }

                fun build(value: Packet): IntOrPacket {
                    val result = IntOrPacket()
                    result.valuePacket = value
                    return result
                }
            }

        }

        class Packet : Comparable<Packet>{
            private var father: Packet? = null
            internal val values = mutableListOf<IntOrPacket>()
            fun add(value: Int): Packet {
                values.add(IntOrPacket.build(value))
                return this
            }

            fun push(): Packet {
                val child = Packet()
                child.father = this
                values.add(IntOrPacket.build(child))
                return child
            }

            fun pop(): Packet {
                return father!!
            }

            operator fun plus(value: Int): Packet {
                return add(value)
            }

            override fun toString(): String {
                var result = "["
                for ((i, v) in values.withIndex()) {
                    if (v.isInt()) {
                        result += v.toInt()
                    } else {
                        result += v.toPacket().toString()
                    }
                    if (i < values.size - 1 ) {
                        result += ","
                    }
                }
                result += "]"
                return result
            }

            override operator fun compareTo(other: Packet): Int {
                for ((i, l) in values.withIndex()) {
                    val r = if (other.values.size > i) other.values[i] else null
                    if (r != null) {
                        val c = l.compareTo(r)
                        if (c != 0) {
                            return c
                        }
                    }
                }
                return values.size.compareTo(other.values.size)
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other?.javaClass != javaClass) return false

                other as Packet

                for ((i, v) in values.withIndex()) {
                    val o = if (other.values.size > i) other.values[i] else null
                    if (o == null) {
                        return false
                    } else {
                        if (v != o) {
                            return false
                        }
                    }
                }
                return true
            }

            override fun hashCode(): Int {
                var result = father?.hashCode() ?: 0
                result = 31 * result + values.hashCode()
                return result
            }

            companion object {

                fun parsePacket(inputPacket: Packet, line: String) {
                    fun addAndClean(strDigits: String, packet: Packet): String {
                        var result = strDigits
                        if (result.isNotEmpty()) {
                            packet.add(result.toInt())
                            result = ""
                        }
                        return result
                    }

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

                fun createAndParsePacket(line: String): Packet {
                    val result = Packet()
                    parsePacket(result, line)
                    return result
                }
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

                PacketPair.Packet.parsePacket(packetPair.left(), first)
                PacketPair.Packet.parsePacket(packetPair.rigth(), second)
            }
            return result
        }

        fun packets(fileName: String): List<PacketPair.Packet> {
            return File(fileName).readLines().filter { it.trim().isNotEmpty() }.map { PacketPair.Packet.createAndParsePacket(it) }
        }


    }

}
