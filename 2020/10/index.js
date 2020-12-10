const { arrangements, arrangement, adaptersBag } = require("./adapter");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    const adapters = adaptersBag(data);
    const a = arrangement(0, adapters);
    //const as = arrangements(a.sequence);

    console.log(a.distribution, `${a.distribution["1"] * a.distribution["3"]}`);
    //console.log(`${as.length}`);

})();
