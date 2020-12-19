const gifts = (text) => text.trim().split("\n").map(line => line.trim().split("x").map(n => parseInt(n.trim())))

const paper = (g) => {
    const minSurface = g[0] * g[1] < g[0] * g[2] ? (g[0] * g[1] < g[1] * g[2] ? [g[0], g[1]] : [g[1], g[2]]) : (g[0] * g[2] < g[1] * g[2] ? [g[0] , g[2]] : [g[1], g[2]]);
    return 2 * g[0] * g[1] + 2 * g[0] * g[2] + 2 * g[1] * g[2] + minSurface[0] * minSurface[1];
}

const totalPaper = (gs) => gs.reduce((p, n) => p + paper(n), 0);

const totalPape1r = (gs) => {
    let c = 0
    for (g of gs) {
        c += paper(g);
        console.log(c, g);
    }
}

module.exports = {
    totalPaper,
    paper,
    gifts
}