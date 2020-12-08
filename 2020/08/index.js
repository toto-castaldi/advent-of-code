const { program, accumulator, fixAndRun } = require("./handheld");
const fs = require("fs").promises;

(async () => {
    try {
        const data = await fs.readFile("./input.txt", "utf8");
        const prg = program(data);

        console.log(`${accumulator(prg)}`);

        const fixed = fixAndRun(prg);

        console.log(`${fixed.accumulatorValue}`);
    } catch (err) {
        console.log(err)
    }
})();
