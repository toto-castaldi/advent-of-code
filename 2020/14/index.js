const { program, runV1, runV2 } = require("./docking");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    let prg = program(data);
          
    let mem = runV1(prg);

    let acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);

    console.log(`${acc}`); 
    
    mem = runV2(prg);

    acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);

    console.log(`${acc}`);

})();
