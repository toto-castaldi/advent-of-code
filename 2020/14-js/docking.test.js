const { program, runV1, runV2, binToDec, applyMaskV1, applyMaskV2,  floatings } = require("./docking");

test("challenge-step-1", () => {
    const prg = program(`
    mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
    mem[8] = 11
    mem[7] = 101
    mem[8] = 0
    `);

    expect(binToDec("1001")).toBe(9);
    expect(binToDec("0000000001001")).toBe(9);
    expect(applyMaskV1("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X", 0)).toBe(64);

    let mem = runV1(prg);

    let acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);
    
    expect(acc).toBe(165);
});

test("challenge-step-2", () => {
    const prg = program(`
    mask = 000000000000000000000000000000X1001X
    mem[42] = 100
    mask = 00000000000000000000000000000000X0XX
    mem[26] = 1
    `);

    let memMask = applyMaskV2("000000000000000000000000000000X1001X", 42);

    expect(memMask).toBe("000000000000000000000000000000X1101X");

    const floatingValues = floatings(memMask);
    expect(floatingValues).toContain(26);
    expect(floatingValues).toContain(27);
    expect(floatingValues).toContain(58);
    expect(floatingValues).toContain(59);
    expect(floatingValues).toHaveLength(4);

    memMask = applyMaskV2("00000000000000000000000000000000X0XX", 26);

    expect(memMask).toBe("00000000000000000000000000000001X0XX");

    let mem = runV2(prg);

    let acc = Object.keys(mem).reduce((p, c, i) => p + mem[c], 0);
    
    expect(acc).toBe(208);  
});
