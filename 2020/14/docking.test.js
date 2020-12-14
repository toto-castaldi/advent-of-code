const { program, run, binToDec, applyMask } = require("./docking");

test("challenge-step-1", () => {
    const prg = program(`
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
    `);

    expect(binToDec("1001")).toBe(9);
    expect(binToDec("0000000001001")).toBe(9);
    expect(applyMask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", 0)).toBe(64);

    let mem = run(prg);

    console.log(mem);

    let acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);
    
    expect(acc).toBe(165);
});
