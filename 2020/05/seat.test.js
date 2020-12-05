const {seat} = require('./seat');

test('challenge-input-1', () => {
    const res = seat('BFFFBBFRRR');
    expect(res.row).toBe(70);
    expect(res.column).toBe(7);
    expect(res.id).toBe(567);
});
test('challenge-input-2', () => {
    const res = seat('FFFBBBFRRR');
    expect(res.row).toBe(14);
    expect(res.column).toBe(7);
    expect(res.id).toBe(119);
});
test('challenge-input-3', () => {
    const res = seat('BBFFBBFRLL');
    expect(res.row).toBe(102);
    expect(res.column).toBe(4);
    expect(res.id).toBe(820);
});