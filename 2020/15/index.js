const { newMatch, turn, run } = require("./memory");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");

    console.log(`${run(data, 2020)}`);

})();
