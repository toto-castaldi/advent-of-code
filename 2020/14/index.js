const { program, run } = require("./docking");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    let prg = program(data);
       
    let mem = run(prg);

    let acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);


    console.log(`${acc}`);

})();
