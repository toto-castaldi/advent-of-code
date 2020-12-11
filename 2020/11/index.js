const { seats, stabilize, countSeats, adjacent, sight } = require("./ferry");
const fs = require("fs").promises;

(async () => {
  const data = await fs.readFile("./input.txt", "utf8");
  const a = seats(data);

  const stab1 = stabilize(a, adjacent, 4);
  console.log(countSeats(stab1.seatMap, "#"));

  const stab2 = stabilize(a, sight, 5);
  console.log(countSeats(stab2.seatMap, "#"));

})();
