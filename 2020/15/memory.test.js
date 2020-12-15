const { newMatch, turn, run } = require("./memory");

test("challenge-step-1", () => {
    expect(run("0,3,6", 2020)).toBe(436);
    expect(run("1,3,2", 2020)).toBe(1);
    expect(run("2,1,3", 2020)).toBe(10);
    expect(run("1,2,3", 2020)).toBe(27);
    expect(run("3,1,2", 2020)).toBe(1836);
});