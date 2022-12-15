import java.io.File

fun main(args: Array<String>) {
    val test = args.size > 1 && args[1] == "1"
    val fileName = { yyyyDd: String -> "${yyyyDd + File.separator + (if (test) "test-input.txt" else "input.txt")}"}

    when (val yyyyDd = args.firstOrNull()?.let { it } ?: "") {
        "2022/05" -> Aoc202205(fileName(yyyyDd)).run()
        "2022/10" -> Aoc202210(fileName(yyyyDd)).run()
        "2022/11" -> {
            val aoc202211 = Aoc202211.build(fileName(yyyyDd))
            println(aoc202211.part1())
            println(aoc202211.part2())
        }
        "2022/12" -> Aoc202212(fileName(yyyyDd)).run()
        "2022/13" -> {
            val aoc202213: Aoc202213 = Aoc202213.build(fileName(yyyyDd))
            val correctOrderIndexes = aoc202213.correctOrderIndexes()
            println(correctOrderIndexes.fold(0) { acc, value -> acc + value })

            val firstDivider = Aoc202213.PacketPair.Packet.createAndParsePacket("[[2]]")
            val secondDivider = Aoc202213.PacketPair.Packet.createAndParsePacket("[[6]]")
            val packets = Aoc202213.packets(fileName(yyyyDd)).toMutableList()
            packets.add(firstDivider)
            packets.add(secondDivider)

            packets.sort()

            val firstIndex = packets.indexOf(firstDivider) + 1
            val secondIndex = packets.indexOf(secondDivider) + 1

            println("$firstIndex $secondIndex ${firstIndex * secondIndex}")
        }
        "2022/14" -> {
            val aoc202214Part1 = Aoc202214.parsePaths(Aoc202214(500), File(fileName(yyyyDd)).readLines())
            println(aoc202214Part1.blockedSandCount())

            val aoc202214Part2 = Aoc202214.parsePaths(Aoc202214(500), File(fileName(yyyyDd)).readLines())
            aoc202214Part2.addFloor(2)
            println(aoc202214Part2.blockedSandCount())
        }
        "2022/15" -> {
            println(Aoc202215.run(fileName(yyyyDd), if (test) 10 else 2000000 ))
        }
        else -> {
            println("please specify correct YYYY/DD ($yyyyDd)")
        }
    }

}
