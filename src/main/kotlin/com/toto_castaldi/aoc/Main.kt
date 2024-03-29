import java.io.File
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    val elapsed = measureTime {
        val test = args.size > 1 && args[1] == "1"
        val fileName = { yyyyDd: String -> yyyyDd + File.separator + (if (test) "test-input.txt" else "input.txt") }

        when (val yyyyDd = args.firstOrNull() ?: "") {
            "2017/05" -> {
                Aoc201705.run1(fileName(yyyyDd))
                Aoc201705.run2(fileName(yyyyDd))
            }
            "2017/06" -> {
                Aoc201706.run1(fileName(yyyyDd))
                Aoc201706.run2(fileName(yyyyDd))
            }
            "2017/07" -> {
                Aoc201707.run1(fileName(yyyyDd))
            }
            "2018/03" -> {
                Aoc201803.run1(fileName(yyyyDd))
                Aoc201803.run2(fileName(yyyyDd))
            }
            "2019/02" -> {
                Aoc201902.run1(fileName(yyyyDd))
            }
            "2019/03" -> {
                Aoc201903.run1(fileName(yyyyDd))
            }
            "2019/04" -> {
                Aoc201904.run1(271973,785961)
                Aoc201904.run2(271973,785961)
            }
            "2019/06" -> Aoc201906.run1(fileName(yyyyDd))
            "2022/05" -> Aoc202205(fileName(yyyyDd)).run()
            "2022/10" -> Aoc202210(fileName(yyyyDd)).run()
            "2022/11" -> {
                println(Aoc202211.build(fileName(yyyyDd)).part1())
                println(Aoc202211.build(fileName(yyyyDd)).part2())
            }
            "2022/12" -> {
                Aoc202212().part1(fileName(yyyyDd))
                Aoc202212().part2(fileName(yyyyDd))
            }
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
                println(Aoc202215.run1(fileName(yyyyDd), if (test) 10 else 2000000))
                println(Aoc202215.run2(fileName(yyyyDd), if (test) 20 else 4000000))
            }
            "2022/16" -> {
                println(Aoc202216.run1(fileName(yyyyDd)))
                println(Aoc202216.run2(fileName(yyyyDd)))
            }
            "2022/17" -> {
                Aoc202217.run1(fileName(yyyyDd), 2022)
                Aoc202217.run2(fileName(yyyyDd), 6000,1000000000000)
            }
            "2022/18" -> {
                Aoc202218.run1(fileName(yyyyDd))
                Aoc202218.run2(fileName(yyyyDd))
            }
            "2022/19" -> {
                Aoc202219.run1(fileName(yyyyDd))
                Aoc202219.run2(fileName(yyyyDd))
            }
            "2022/20" -> {
                Aoc202220.run1(fileName(yyyyDd))
                Aoc202220.run2(fileName(yyyyDd))
            }
            "2022/21" -> {
                Aoc202221.run1(fileName(yyyyDd))
                Aoc202221.run2(fileName(yyyyDd))
            }
            "2022/22" -> {
                Aoc202222Part1.run(fileName(yyyyDd))
                Aoc202222Part2.run(fileName(yyyyDd), test)
            }
            "2022/23" -> {
                Aoc202223.run1(fileName(yyyyDd))
                Aoc202223.run2(fileName(yyyyDd))
            }
            "2022/24" -> {
                Aoc202224.run1(fileName(yyyyDd))
            }

            else -> {
                println("please specify correct YYYY/DD ($yyyyDd)")
            }
        }
    }
    println("execution time $elapsed")

}
