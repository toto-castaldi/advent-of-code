import java.io.File

private class DeviceFolder(val name: String, val parent: DeviceFolder?) {

    val folders = mutableListOf<DeviceFolder>()
    val files = mutableListOf<DeviceFile>()

    fun getFolder(folderName: String): DeviceFolder {
        return folders.find { it -> it.name == folderName }!!
    }

    fun addFolder(folderName: String) {
        folders.add(DeviceFolder(folderName, this))
    }

    fun addFile(fileSize: Long, fileName: String) {
        files.add(DeviceFile(fileName, fileSize))
    }

    override fun toString(): String {
        return name
    }

    fun totalDimension(): Long {
        var result = 0L
        for (d in folders) {
            result += d.totalDimension()
        }
        for (f in files) {
            result += f.dimension
        }
        return result
    }

    fun printAll(indentLevel : Long) {
        println((0..indentLevel).fold("") { acc, _ -> "$acc " } + "- $name (dir) - ${this.totalDimension()}")
        for (d in folders) {
            d.printAll(indentLevel + 2)
        }
    }

}

private data class DeviceFile(val name: String, val dimension: Long)


fun main(args: Array<String>) {
    test()
    val rootFolder = DeviceFolder("/", null)
    var currentFolder: DeviceFolder = rootFolder
    File(args[0]).forEachLine {
        line ->

        if (line.startsWith("\$")) { //command
            when (line.substring(2, 4)) {
                "cd" -> {
                    if (line[5] == '/') {
                        currentFolder = rootFolder
                    } else {
                        val folderName = line.substring(5)
                        if (folderName == "..") {
                            currentFolder = currentFolder.parent!!
                        } else {
                            currentFolder = currentFolder.getFolder(folderName)
                        }
                    }
                }
                "ls" -> {
                }
                else -> println("unknow command")
            }
        } else { //folder content
            if (line.startsWith("dir ")) {
                currentFolder.addFolder(line.substring(4))
            } else {
                val (fileSize, fileName) = line.split(' ')
                currentFolder.addFile(fileSize.toLong(), fileName)
            }
        }
    }

    var total = 0L
    var bigEnough = mutableListOf<DeviceFolder>()

    fun findAndCumulate (theFolder: DeviceFolder, maxSize : Long) {
        for (d in theFolder.folders) {
            val totalDimension = d.totalDimension()
            if (totalDimension <= maxSize) {
                total += totalDimension
            }
            if (totalDimension >= maxSize) {
                bigEnough.add(d)
            }
            findAndCumulate(d, maxSize)
        }
    }
    findAndCumulate(rootFolder, 100000L)
    println(total)

    val diskSpace = 70000000L
    val updateSpace = 30000000L
    val usedSpace = rootFolder.totalDimension()
    val unusedSpace = diskSpace - usedSpace
    val needSpace = updateSpace - unusedSpace
    println("used space $usedSpace")
    println("unused space $unusedSpace")
    println("need space $needSpace")

    rootFolder.printAll(0)
    total = 0
    bigEnough = mutableListOf<DeviceFolder>()
    findAndCumulate(rootFolder, needSpace)

    bigEnough.sortBy { it.totalDimension() }

    val f = bigEnough.first()

    println("$f -> ${f.totalDimension()}")

    println("${f.totalDimension() - needSpace}")
}


private fun test() {
    assert(42 == 42)
}

