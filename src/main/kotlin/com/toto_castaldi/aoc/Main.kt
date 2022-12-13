fun main(args: Array<String>) {
    when (args[0]) {
        "202213" -> {
            val aoc202213: Aoc202213 = Aoc202213.build(args[1])
            val correctOrderIndexes = aoc202213.correctOrderIndexes()
            println(correctOrderIndexes.fold(0) { acc, value -> acc + value })

            val firstDivider = Aoc202213.PacketPair.Packet.createAndParsePacket("[[2]]")
            val secondDivider = Aoc202213.PacketPair.Packet.createAndParsePacket("[[6]]")
            val packets = Aoc202213.packets(args[1]).toMutableList()
            packets.add(firstDivider)
            packets.add(secondDivider)

            packets.sort()

            val firstIndex = packets.indexOf(firstDivider) + 1
            val secondIndex = packets.indexOf(secondDivider) + 1

            println("$firstIndex $secondIndex ${firstIndex * secondIndex}")
        }
    }

}