const { trasmission, findWrongPacket, findWeakness } = require("./cypher");

test("challenge-input-1", () => {
    const trx = trasmission(`
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
    `);
    expect(findWrongPacket(5, trx)).toBe(127);
});

test("challenge-input-2", () => {
    const trx = trasmission(`
    35
    20
    15
    25
    47
    40
    62
    55
    65
    95
    102
    117
    150
    182
    127
    219
    299
    277
    309
    576
    `);
    const weakness = findWeakness(127, trx);
    expect(weakness.min + weakness.max).toBe(62);
});