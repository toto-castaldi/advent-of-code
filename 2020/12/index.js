const { path, navigate, manhattanDistance } = require("./ship");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const p = path(data);

  const n = navigate(p);
  console.log(n, manhattanDistance(n));

})();
