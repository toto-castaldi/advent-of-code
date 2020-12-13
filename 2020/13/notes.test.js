const { schedule, nextBus, earliestAllMatchesOffset } = require("./notes");

test("challenge-step-1", () => {
    const s = schedule(`
    939
    7,13,x,x,59,x,31,19
    `);

    let nb = nextBus(s);

    expect(nb.id).toBe(59);
    expect(nb.time).toBe(944);
    
    let e = earliestAllMatchesOffset(s);

    expect(e).toBe(1068781);
});

test("challenge-step-2.1", () => {
    const s = schedule(`
    17,x,13,19
    `);

    //console.log(s);

    let nb = earliestAllMatchesOffset(s);

    expect(nb).toBe(3417);
});


test("challenge-step-2.2", () => {
    const s = schedule(`
    67,7,59,61
    `);

    let nb = earliestAllMatchesOffset(s);

    expect(nb).toBe(754018);
});

test("challenge-step-2.5", () => {
    const s = schedule(`
    1789,37,47,1889
    `);

    let nb = earliestAllMatchesOffset(s);

    expect(nb).toBe(1202161486);
});
