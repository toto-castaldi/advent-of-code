const { arrangements, arrangement, adaptersBag } = require("./adapter");

test("challenge-input-1", () => {
    const a = arrangement(0, adaptersBag(`
    16
    10
    15
    5
    1
    11
    7
    19
    6
    12
    4
    `));
    const as = arrangements(a.sequence);
    expect(a.distribution).toStrictEqual({ "1": 7, "3": 5 });
    expect(a.device).toBe(22);
    console.table(as);
    expect(as).toHaveLength(8);
});

test("challenge-input-2", () => {
    const a = arrangement(0,adaptersBag(`
    28
    33
    18
    42
    31
    14
    46
    20
    48
    47
    24
    23
    49
    45
    19
    38
    39
    11
    1
    32
    25
    35
    8
    17
    7
    9
    4
    2
    34
    10
    3
    `));
    const as = arrangements(a.sequence);
    expect(a.distribution).toStrictEqual({ "1": 22, "3": 10 });
    expect(as).toHaveLength(19208);
});