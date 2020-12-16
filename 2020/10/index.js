const { arrangements, arrangement, adaptersBag } = require("./adapter");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const _adapters = adaptersBag(data);
  const adapters = adaptersBag(`
    16
    10
    15
    5
    1
    11
    7
    19
    6
    12
    4
    `);
  const __adapters = adaptersBag(`
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
    `);

  const a = arrangement(0, adapters);

  const as = arrangements(a.sequence);

  console.table(as);

})();
