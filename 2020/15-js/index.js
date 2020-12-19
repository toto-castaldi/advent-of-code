const { newMatch, turn, run } = require("./memory");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");

    console.log(`${run(data, 2020)}`);
    console.log(`${run(data, 30000000)}`);

})();
