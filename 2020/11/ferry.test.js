const { seats, stabilize, countSeats, adjacent } = require("./ferry");

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
    expect(adjacent(-1,-1,0,0,a)).toBe(undefined);
    expect(adjacent(-1,-1,1,1,a)).toBe("L");

    const stCount = stabilize(a);
    expect(countSeats(stCount.seatMap, "#")).toBe(37);
});
