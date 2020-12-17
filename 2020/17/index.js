const { round, space, layer } = require("./conway");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");

    let _sp = space(data);
    let sp = space(`
    .#.
    ..#
    ###
    `);

    sp = round(sp);

    console.table(layer(sp, -1));
    console.table(layer(sp, 0));
    console.table(layer(sp, 1));

})();
