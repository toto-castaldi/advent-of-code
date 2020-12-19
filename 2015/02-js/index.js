const { gifts, totalPaper, paper } = require("./gift");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    try {
        const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
        const gs = gifts(data);

        const tp = totalPaper(gs);

        console.log(`${tp}`); 

    } catch (err) {
        console.log(err)
    }
})();
