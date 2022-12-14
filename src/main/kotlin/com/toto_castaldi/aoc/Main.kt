import java.io.File

fun main(args: Array<String>) {
    when (args[0]) {
        "202211" -> {
            val aoc202211 = Aoc202211.build(args[1])
            println(aoc202211.part1())
            println(aoc202211.part2())
        }
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
        "202214" -> {
            val aoc202214Part1 = Aoc202214.parsePaths(Aoc202214(500), File(args[1]).readLines())
            println(aoc202214Part1.blockedSandCount())

            val aoc202214Part2 = Aoc202214.parsePaths(Aoc202214(500), File(args[1]).readLines())
            aoc202214Part2.addFloor(2)
            println(aoc202214Part2.blockedSandCount())
        }
    }

}