package com.toto_castaldi.common.structure

/**
..........#.................
.........###................
........#####...............
.......#######..............
......##########............
.....#############..........
....###############.........
...#################........
....###############.........
.....#############..........
......###########...........
.......#########............
........#######.............
.........#####..............
..........###...............
...........#................
 */
class Diamond(val center: Coordinates, val halfWidth: Int) {

    private var a: Coordinates = Coordinates(center.x, center.y - halfWidth)
    private var b: Coordinates = Coordinates(center.x + halfWidth, center.y)
    private var c: Coordinates = Coordinates(center.x, center.y + halfWidth)
    private var d: Coordinates = Coordinates(center.x - halfWidth, center.y)

    operator fun contains(coordinates: Coordinates): Boolean {
        val interval = resolveInterval(coordinates)
        return interval != null && interval.contains(coordinates.x)
    }

    fun resolveInterval(y: Int): IntRange? {
        return resolveInterval(Coordinates(center.x, y))
    }
    fun resolveInterval(coordinates: Coordinates): IntRange? {
        var interval: IntRange? = null
        //val (x, y) = coordinates
        val x = coordinates.x
        val y = coordinates.y
        if (y == a.y || y == c.y) {
            interval = x..x
        } else if (y == b.y) {
            interval = d.x..b.x
        } else if (y > a.y && y < d.y) {
            val f = Coordinates(Line(a, d).atY(y).toInt(), y)
            val s = Coordinates(Line(a, b).atY(y).toInt(), y)
            interval = f.x..s.x
        } else if (y > d.y && y < c.y) {
            val f = Coordinates(Line(d, c).atY(y).toInt(), y)
            val s = Coordinates(Line(b, c).atY(y).toInt(), y)
            interval = f.x..s.x
        }
        return interval
    }
}