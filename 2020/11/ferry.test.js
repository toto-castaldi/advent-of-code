const { seats, stabilize, countSeats } = require("./ferry");

test("challenge-input-1", () => {
    const a = seats(`
    L.LL.LL.LL
    LLLLLLL.LL
    L.L.L..L..
    LLLL.LL.LL
    L.LL.LL.LL
    L.LLLLL.LL
    ..L.L.....
    LLLLLLLLLL
    L.LLLLLL.L
    L.LLLLL.LL
    `);
    const stCount = stabilize(a);
    expect(countSeats(stCount.seatMap, "#")).toBe(37);
});
