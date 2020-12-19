const { schedule, nextBus } = require("./notes");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
    let s = schedule(data);
       
    let nb = nextBus(s);

    console.log(`${nb.id * (nb.time - s.earliest)}`);

})();
