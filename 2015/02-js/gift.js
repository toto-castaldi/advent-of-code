const gifts = (text) => text.trim().split("\n").map(line => line.trim().split("x").map(n => parseInt(n.trim())))

const paper = (g) => {
    const minSurface = g[0] * g[1] < g[0] * g[2] ? (g[0] * g[1] < g[1] * g[2] ? [g[0], g[1]] : [g[1], g[2]]) : (g[0] * g[2] < g[1] * g[2] ? [g[0], g[2]] : [g[1], g[2]]);
    return 2 * g[0] * g[1] + 2 * g[0] * g[2] + 2 * g[1] * g[2] + minSurface[0] * minSurface[1];
}

const ribbon = (g) => {
    const firstMin = Math.min(...g);
    let gClone = [...g];
    gClone.splice(gClone.indexOf(firstMin), 1);
    const secondMin = Math.min(...gClone);
    return firstMin * 2 + secondMin * 2 + g[0] * g[1] * g[2];
}

const totalOf = (gs, f) => gs.reduce((p, n) => p + f(n), 0);

module.exports = {
    totalOf,
    paper,
    gifts,
    ribbon
}