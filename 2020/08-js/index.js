const { program, accumulator, fixAndRun } = require("./handheld");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    try {
        const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
        const prg = program(data);

        console.log(`${accumulator(prg)}`);

        const fixed = fixAndRun(prg);

        console.log(`${fixed.accumulatorValue}`);
    } catch (err) {
        console.log(err)
    }
})();
