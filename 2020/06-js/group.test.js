const { group, anyone, everyone } = require("./group");

test("challenge-input-1", () => {
    const g = group(`
    abc
    `);
    expect(anyone(g)).toBe(3);
    expect(everyone(g)).toBe(3);
});
test("challenge-input-2", () => {
    const g = group(`
    a
    b
    c
    `);
    expect(anyone(g)).toBe(3);
    expect(everyone(g)).toBe(0);
});
test("challenge-input-3", () => {
    const g = group(`
    ab
    ac
    `);
    expect(anyone(g)).toBe(3);
    expect(everyone(g)).toBe(1);
});
test("challenge-input-4", () => {
    const g = group(`
    a
    a
    a
    a
    `);
    expect(anyone(g)).toBe(1);
    expect(everyone(g)).toBe(1);
});
test("challenge-input-5", () => {
    const g = group(`
    b
    `)
    expect(anyone(g)).toBe(1);
    expect(everyone(g)).toBe(1);
});