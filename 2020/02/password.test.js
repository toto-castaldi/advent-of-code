const { isValidPartOne, isValidPartTwo } = require("./password");

test('part-one', () => {
    expect(isValidPartOne("1-3 a: abcde")).toBeTruthy();
    expect(isValidPartOne("1-3 b: cdefg")).toBeFalsy();
    expect(isValidPartOne("2-9 c: ccccccccc")).toBeTruthy();
});

test('part-two', () => {
    expect(isValidPartTwo("1-3 a: abcde")).toBeTruthy();
    expect(isValidPartTwo("1-3 b: cdefg")).toBeFalsy();
    expect(isValidPartTwo("2-9 c: ccccccccc")).toBeFalsy();
    expect(isValidPartTwo("2-9 c: acaaaaaab")).toBeTruthy();
    expect(isValidPartTwo("2-9 c: abaaaaaac")).toBeTruthy();
});