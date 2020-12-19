const { arrangement, adaptersBag } = require("./adapter");
const fs = require("fs").promises;
const path = require("path");

(async () => {
  const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
  const adapters = adaptersBag(data);

  const a = arrangement(0, adapters);

  console.log(a);
  console.log(a.distribution["1"] * a.distribution["3"]);
  

})();
