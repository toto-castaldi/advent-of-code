const space = (text) => [text.trim().split("\n").map(line => line.trim().split(""))]

const layer = (spc, z) => spc[parseInt(spc.length / 2) + z]

const plane = (length, el) => el.repeat(length).split("").map(r => el.repeat(length).split(""))

const sumIf = (char) => (p, c, i) => p + (c === char ? 1 : 0)
const sum = (p, c, i) => p + c

const count = (spc, zEl, xEl, yEl, maxCount, char) => {
    let result = 0;
    for (let z = (zEl > 0 ? zEl - 1 : 0); z < (zEl < 3 ? zEl + 1 : zEl); z++) {
        for (let x = (xEl > 0 ? xEl - 1 : 0); x < (xEl < 3 ? xEl + 1 : xEl); x++) {
            for (let y = (yEl > 0 ? yEl - 1 : 0); y < (yEl < 3 ? yEl + 1 : yEl); y++) {
                //console.log(z, x, y, spc[z][x][y]);
                if (z - zEl + x + xEl + y - yEl !== 0) {
                    result += spc[z][x][y] === char ? 1 : 0;
                    if (result >= maxCount) return result;
                }
            }
        }
    }
    return result;

}

const round = (spc) => {
    let result = [];
    let spaceEn = [];
    let len = spc[0].length + 2;
    for (let z of spc) {
        let rX = [];
        rX.push(".".repeat(len).split(""));
        for (let x of z) {
            const old = [...x];
            old.unshift(".");
            old.push(".");
            rX.push(old);
        }
        rX.push(".".repeat(len).split(""));
        result.push([...rX]);
        spaceEn.push([...rX]);
    }
    result.unshift(plane(len, "."));
    result.push(plane(len, "."));
    spaceEn.unshift(plane(len, "."));
    spaceEn.push(plane(len, "."));

    for (let z = 0; z < result.length; z++) {
        for (let x = 0; x < result[z].length; x++) {
            for (let y = 0; y < result[z][x].length; y++) {
                const cube = spaceEn[z][x][y];

                let c;
                if (cube === "#") {
                    console.log("COUNT", z, x, y, cube);
                     c = count(spaceEn, z, x, y, 4, "#");
                    console.log("COUNT ->", c);
                } else {
                    c = count(spaceEn, z, x, y, 4, "#");
                }
                switch (cube) {
                    case "#":
                        if (c === 2 || c === 3) {
                            result[z][x][y] = "#";
                        } else {
                            result[z][x][y] = ".";
                        }
                        break;
                    case ".":
                        if (c === 3) {
                            result[z][x][y] = "#";
                        } else {
                            result[z][x][y] = ".";
                        }
                        break;
                    default:
                        break;

                }
            }
        }
    }
    return result;
}


module.exports = {
    space,
    round,
    layer,
    plane
}