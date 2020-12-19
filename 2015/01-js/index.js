const { instructions, run, enterLevel } = require("./elevator");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    try {
        const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
        const is = instructions(data);
        console.log(`${run(is)}`);

        console.log(`${enterLevel( is, -1).index }`)

    } catch (err) {
        console.log(err)
    }
})();
