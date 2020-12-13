const { schedule, nextBus, earliestAllMatchesOffset } = require("./notes");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    let s = schedule(data);
    s = schedule(`
    1234
    1789,37,47,1889
    `);
   
    let nb = nextBus(s);

    console.log(`${nb.id * (nb.time - s.earliest)}`);

    console.log(`${earliestAllMatchesOffset(s)}`);


})();
