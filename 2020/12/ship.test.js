const { path, navigate, manhattanDistance } = require("./ship");

test("challenge-input-1", () => {
    const a = path(`
    F10
    N3
    F7
    R90
    F11
    `);
    const n = navigate(a);
    expect(n.x).toBe(17);
    expect(n.y).toBe(-8);
    expect(manhattanDistance(n)).toBe(25); 
});
