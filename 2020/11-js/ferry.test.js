const { seats, stabilize, countSeats, adjacent, sight, round, sameMap } = require("./ferry");

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
    expect(sight(1,0,2,6,a)).toBe("L");
    expect(sight(-1,0,2,6,a)).toBe(undefined);

    expect(sameMap(round(a, adjacent, 4), seats(`
    #.##.##.##
    #######.##
    #.#.#..#..
    ####.##.##
    #.##.##.##
    #.#####.##
    ..#.#.....
    ##########
    #.######.#
    #.#####.##
    `))).toBeTruthy();

    const seats1 = seats(`
    .......#.
    ...#.....
    .#.......
    .........
    ..#L....#
    ....#....
    .........
    #........
    ...#.....
    `);
    expect(seats1[4][3]).toBe("L");
    expect(sight(-1,0,3,4,seats1)).toBe("#");
    expect(sight(1,0,3,4,seats1)).toBe("#");
    expect(sight(-1,-1,3,4,seats1)).toBe("#");
    expect(sight(0,-1,3,4,seats1)).toBe("#");
    expect(sight(1,-1,3,4,seats1)).toBe("#");
    expect(sight(-1,1,3,4,seats1)).toBe("#");
    expect(sight(0,1,3,4,seats1)).toBe("#");
    expect(sight(1,1,3,4,seats1)).toBe("#");

    const stCount1 = stabilize(a, adjacent, 4);
    expect(countSeats(stCount1.seatMap, "#")).toBe(37);

    const stCount2 = stabilize(a, sight, 5);
    expect(countSeats(stCount2.seatMap, "#")).toBe(26);
});
