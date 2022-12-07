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

}
private data class DeviceFile(val name: String, val dimension: Long)

fun main(args: Array<String>) {
    val rootFolder = DeviceFolder("/", null)
    var currentFolder: DeviceFolder = rootFolder
    File(args[0]).forEachLine {
        line ->

        if (line.startsWith("\$")) {
            when (line.substring(2, 4)) {
                "cd" -> {
                    currentFolder = if (line[5] == '/') {
                        rootFolder
                    } else {
                        val folderName = line.substring(5)
                        if (folderName == "..") {
                            currentFolder.parent!!
                        } else {
                            currentFolder.getFolder(folderName)
                        }
                    }
                }
                else -> {}
            }
        } else {
            if (line.startsWith("dir ")) {
                currentFolder.addFolder(line.substring(4))
            } else {
                val (fileSize, fileName) = line.split(' ')
                currentFolder.addFile(fileSize.toLong(), fileName)
            }
        }
    }

    fun navigate (theFolder: DeviceFolder, folderTest : (DeviceFolder) -> Unit ) {
        for (d in theFolder.folders) {
            folderTest(d)
            navigate(d, folderTest)
        }
    }

    var res1 = 0L
    navigate(rootFolder) {
        if (it.totalDimension() <= 100000L) {
            res1 += it.totalDimension()
        }
    }
    println("res1 $res1")

    val diskSpace = 70000000L
    val updateSpace = 30000000L
    val usedSpace = rootFolder.totalDimension()
    val unusedSpace = diskSpace - usedSpace
    val needSpace = updateSpace - unusedSpace
    println("used space $usedSpace")
    println("unused space $unusedSpace")
    println("need space $needSpace")

    var lesserBigEnough = rootFolder
    navigate(rootFolder) {
        if (it.totalDimension() >= needSpace && it.totalDimension() < lesserBigEnough.totalDimension()) {
            lesserBigEnough = it
        }
    }

    println("res2 ${lesserBigEnough.totalDimension()}")
}