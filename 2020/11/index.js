const { seats, stabilize, countSeats } = require("./ferry");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const a = seats(data);
  const stab = stabilize(a);

  const count = countSeats(stab.seatMap, "#");

  console.log(stab.count, count);

})();
