const instructions = (text) => text.trim().split(""); 

const run = (is) => is.reduce((p, n) => p + (n === "(" ? 1 : -1), 0);

const enterLevel = (is, level) => {
    let currentLevel = 0;
    let index = 0;
    while (currentLevel !== level) {
        if (currentLevel === undefined) currentLevel = 0;
        currentLevel += is[index++] === "(" ? 1 : -1;
    }
    return index;
}

module.exports = {
    instructions,
    run,
    enterLevel
}