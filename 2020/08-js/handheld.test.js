const { program, accumulator, fixAndRun } = require("./handheld");

test("challenge-input-1", () => {
    const prg = program(`
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
    `);
    expect(accumulator(prg)).toBe(5);
});

test("challenge-input-2", () => {
    const fixed = fixAndRun(program(`
    nop +0
    acc +1
    jmp +4
    acc +3
    jmp -3
    acc -99
    acc +1
    jmp -4
    acc +6
    `));
    expect(fixed.accumulatorValue).toBe(8);
    expect(fixed.program[fixed.program.length-1].executed).toBeTruthy();
});