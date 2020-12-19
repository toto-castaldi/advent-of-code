const instructions = (text) => text.trim().split(""); 

const run = (is) => is.reduce((p, n) => p + (n === "(" ? 1 : -1), 0);

module.exports = {
    instructions,
    run
}