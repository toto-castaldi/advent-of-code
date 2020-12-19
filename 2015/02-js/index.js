const { gifts, totalOf, paper, ribbon } = require("./gift");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    try {
        const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
        const gs = gifts(data);

        console.log(`${totalOf(gs, paper)}`); 
        console.log(`${totalOf(gs, ribbon)}`); 

    } catch (err) {
        console.log(err)
    }
})();
