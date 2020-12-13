const { schedule, nextBus } = require("./notes");

test("challenge-step-1", () => {
    const n = schedule(`
    939
    7,13,x,x,59,x,31,19
    `);

    let nb = nextBus(n);

    expect(nb.id).toBe(59);
    expect(nb.time).toBe(944);
});