const { path, manhattanDistance, step, stepTowardsWayPoint } = require("./ship");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const ps = path(data);

  let ship = { x: 0, y: 0, dir: 0 };

  for (let p of ps) ship = step(ship, p);

  console.log(ship, manhattanDistance(ship));

  ship = { x: 0, y: 0, dir: 0 };
  let wayPoint = { x: 10, y: 1 };

  for (let p of ps) ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, p));

  console.log(ship, manhattanDistance(ship));

})();
