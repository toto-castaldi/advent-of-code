const seats = (text) => text.trim().split("\n").map(line => line.trim().split(""));

const countSeats = (map, car) => map.reduce((prev, curr) => prev + curr.reduce((prev, curr) => prev + (curr === car ? 1 : 0), 0), 0);

const adjacent = (dirX, dirY, x, y, map) => (y + dirY >= 0 && y + dirY < map.length) ? map[y + dirY][x + dirX] : undefined;

const stabilize = (seatMap) => {
    let count = -1;
    let oldMap = [];

    const sameMap = (a, b) => {
        if (a.length !== b.length) return false;
        for (let i = 0; i < a.length; i++) {
            let aRow = a[i];
            let bRow = b[i];
            if (JSON.stringify(aRow) !== JSON.stringify(bRow)) return false;
        }
        return true;
    }



    const round = (map) => {
        let result = [];
        for (let mapRow of map) {
            result.push([...mapRow]);
        }
        for (let i = 0; i < result.length; i++) {
            for (j = 0; j < result[i].length; j++) {
                const seat = map[i][j];
                switch (seat) {
                    case "L":
                        if (
                            [undefined, ".", "L"].includes(adjacent(-1, -1, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(0, -1, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(1, -1, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(-1, 0, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(1, 0, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(-1, 1, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(0, 1, j, i, map)) &&
                            [undefined, ".", "L"].includes(adjacent(1, 1, j, i, map))
                        ) {
                            result[i][j] = "#";
                        }
                        break;
                    case "#":
                        let count = 0;
                        if (adjacent(-1, -1, j, i, map) === "#" ) count ++;
                        if (adjacent(0, -1, j, i, map) === "#" ) count ++;
                        if (adjacent(1, -1, j, i, map) === "#" ) count ++;
                        if (adjacent(-1, 0, j, i, map) === "#" ) count ++;
                        if (adjacent(1, 0, j, i, map) === "#" ) count ++;
                        if (adjacent(-1, 1, j, i, map) === "#" ) count ++;
                        if (adjacent(0, 1, j, i, map) === "#" ) count ++;
                        if (adjacent(1, 1, j, i, map) === "#" ) count ++;
                        if (count >= 4) {
                            result[i][j] = "L";
                        }
                        break;
                    default:
                        break;

                }
            }
        }
        return result;
    }

    while (!sameMap(seatMap, oldMap)) {
        oldMap = seatMap;
        seatMap = round(seatMap);
        count++;
    }

    return { count, seatMap };
}


module.exports = { seats, stabilize, countSeats, adjacent }