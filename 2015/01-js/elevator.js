const instructions = (text) => text.trim().split("");

const run = (is) => is.reduce((p, n) => p + (n === "(" ? 1 : -1), 0)

const enterLevel = (is, level) => is.reduce((p, n, i) => { let sum = p.sum + (n === "(" ? 1 : -1); return  { sum, index: sum === level && p.index === 0 ? i : p.index } }, { sum: 0, index: 0 })

module.exports = {
    instructions,
    run,
    enterLevel
}