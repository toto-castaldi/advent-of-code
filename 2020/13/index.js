const { schedule, nextBus } = require("./notes");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const s = schedule(data);

  let nb = nextBus(s);

  console.log(`${nb.id * (nb.time - s.earliest)}`)

})();
