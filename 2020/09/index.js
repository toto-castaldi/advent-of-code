const { trasmission, findWrongPacket, findWeakness } = require("./cypher");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    const trx = trasmission(data);

    const wrongPacket = findWrongPacket(25, trx);

    console.log(`${wrongPacket}`);

    const weakness = findWeakness(wrongPacket, trx);
    console.log(`${weakness.min + weakness.max}`);

})();
