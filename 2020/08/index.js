const { program, accumulator, fixAndRun } = require("./handheld");
const fs = require("fs");

fs.readFile("./input.txt", "utf8", function (err, data) {
    if (err) {
        return console.log(err);
    }
    const prg = program(data);

    console.log(`${accumulator(prg)}`);

    const fixed = fixAndRun(prg);

    console.log(`${fixed.accumulatorValue}`);
});