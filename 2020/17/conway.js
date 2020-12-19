const store = (data, z, x, y) => {
    if (data[z] === undefined) data[z] = [];
    const dataZ = data[z];
    if (dataZ[x] === undefined) dataZ[x] = [];
    const dataX = dataZ[x];
    dataX[y] = "#";
}

const space = (text) => {
    const data = [];
    const rawData = text.trim().split("\n").map(line => line.trim().split(""));
    for (let x = 0; x < rawData[0].length; x++) {
        const row = rawData[x];
        for (let y = 0; y < row.length; y++) {
            const el = row[y];
            if (el === "#") {
                store(data, 0, x, y);
            }
        }
    }
    return {
        data,
        dimension: rawData[0].length
    }

}

const navigate = (zI, xI, yI) => {
    let result = [];
    for (let z = -1; z <= +1; z++) {
        for (let x = -1; x <= +1; x++) {
            for (let y = -1; y <= +1; y++) {
                if (z !== 0 && x !== 0 && y !== 0) {
                    result.push([z + zI, x + xI, y + yI]);
                }
            }
        }
    }
    return result;
}

const actives = (spc) => {
    let result = [];
    for (let z = 0; z < spc.data.length; z++) {
        for (let x = 0; x < spc.data[z].length; x++) {
            for (let y = 0; y < spc.data[z][x].length; y++) {
                if (isActive(spc.data, z, x, y)) {
                    result.push([z, x, y]);
                }
            }
        }
    }
    return result;
}

const isActive = (data, z, x, y) => data[z] && data[z][x] && data[z][x][y]

const round = (spc) => {

    const newData = [];
    const acts = actives(spc);
    for (let a of acts) {
        let neigActive = 0;
        let nav = navigate(a[0], a[1], a[2]);
        for (let i = 0; i < nav.length && neigActive < 4; i++) {
            if (isActive(spc.data, nav[i][0], nav[i][1], nav[i][2])) neigActive++;
        }
        if (neigActive == 2 || neigActive || 3) {
            store(newData, a[0], a[1], a[2]);
        }
    }
    for (let z = 0; z < spc.dimension; z++) {
        for (let x = 0; x < spc.dimension; x++) {
            for (let y = 0; y < spc.dimension; y++) {
                f(!isActive(spc.data))
            }
        }
    }

    spc.dimension += 2;
    spc.data = newData;


    return spc;
}



module.exports = {
    space,
    actives,
    round
}