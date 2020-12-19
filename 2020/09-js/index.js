const { trasmission, findWrongPacket, findWeakness } = require("./cypher");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
    const trx = trasmission(data);

    const wrongPacket = findWrongPacket(25, trx);

    console.log(`${wrongPacket}`);

    const weakness = findWeakness(wrongPacket, trx);
    console.log(`${weakness.min + weakness.max}`);

})();
