const seats = (text) => text.trim().split("\n").map(line => line.trim().split(""));

const countSeats = (map, car) => map.reduce((prev, curr) => prev + curr.reduce((prev, curr) => prev + (curr === car ? 1 : 0), 0) , 0);

const stabilize = (seatMap) => {
    let count = 0;
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
                            (map[i - 1] === undefined || (map[i - 1] && (map[i - 1][j - 1] === undefined || map[i - 1][j - 1] !== "#"))) &&
                            (map[i - 1] === undefined || (map[i - 1] && (map[i - 1][j] === undefined || map[i - 1][j] !== "#"))) &&
                            (map[i - 1] === undefined || (map[i - 1] && (map[i - 1][j + 1] === undefined || map[i - 1][j + 1] !== "#"))) &&
                            (map[i][j - 1] === undefined || map[i][j - 1] !== "#") &&
                            (map[i][j + 1] === undefined || map[i][j + 1] !== "#") &&
                            (map[i + 1] === undefined || (map[i + 1] && (map[i + 1][j - 1] === undefined || map[i + 1][j - 1] !== "#"))) &&
                            (map[i + 1] === undefined || (map[i + 1] && (map[i + 1][j] === undefined || map[i + 1][j] !== "#"))) &&
                            (map[i + 1] === undefined || (map[i + 1] && (map[i + 1][j + 1] === undefined || map[i + 1][j + 1] !== "#")))
                        ) {
                            result[i][j] = "#";
                        }
                        break;
                    case "#":
                        let count = 0;
                        if (map[i - 1] && (map[i - 1][j - 1] !== undefined && map[i - 1][j - 1] === "#")) count++;
                        if (map[i - 1] && (map[i - 1][j] !== undefined && map[i - 1][j] === "#")) count++;
                        if (map[i - 1] && (map[i - 1][j + 1] !== undefined && map[i - 1][j + 1] === "#")) count++;
                        if (map[i][j - 1] !== undefined && map[i][j - 1] === "#") count++;
                        if (map[i][j + 1] !== undefined && map[i][j + 1] === "#") count++;
                        if (map[i + 1] && (map[i + 1][j - 1] !== undefined && map[i + 1][j - 1] === "#")) count++;
                        if (map[i + 1] && (map[i + 1][j] !== undefined && map[i + 1][j] === "#")) count++;
                        if (map[i + 1] && (map[i + 1][j + 1] !== undefined && map[i + 1][j + 1] === "#")) count++;
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

    count--;

    return { count, seatMap };
}


module.exports = { seats, stabilize, countSeats }