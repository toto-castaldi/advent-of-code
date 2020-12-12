const { path, manhattanDistance, step, stepTowardsWayPoint } = require("./ship");

test("challenge-step-1", () => {
    const ps = path(`
    F10
    N3
    F7
    R90
    F11
    `);

    let ship = { x: 0, y: 0, dir: 0 };

    for (let p of ps) ship = step(ship, p)

    expect(ship.x).toBe(17);
    expect(ship.y).toBe(-8);
    expect(manhattanDistance(ship)).toBe(25);
});

test("to-wp", () => {
    let ship = { x: 0, y: 0, dir: 0 };
    let wayPoint = { x: 10, y: 1 };

    ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, { instruction: "F", instructionValue: 10 }));
    expect(ship.x).toBe(100);
    expect(ship.y).toBe(10);
    expect(wayPoint.x).toBe(10);
    expect(wayPoint.y).toBe(1);

    ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, { instruction: "N", instructionValue: 3 }));
    expect(ship.x).toBe(100);
    expect(ship.y).toBe(10);
    expect(wayPoint.x).toBe(10);
    expect(wayPoint.y).toBe(4);

    ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, { instruction: "F", instructionValue: 7 }));
    expect(ship.x).toBe(170);
    expect(ship.y).toBe(38);
    expect(wayPoint.x).toBe(10);
    expect(wayPoint.y).toBe(4);

    ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, { instruction: "R", instructionValue: 90 }));
    expect(ship.x).toBe(170);
    expect(ship.y).toBe(38);
    expect(wayPoint.x).toBe(4);
    expect(wayPoint.y).toBe(-10);

    ({ ship, wayPoint } = stepTowardsWayPoint(ship, wayPoint, { instruction: "F", instructionValue: 11 }));
    expect(ship.x).toBe(214);
    expect(ship.y).toBe(-72);
    expect(wayPoint.x).toBe(4);
    expect(wayPoint.y).toBe(-10);

    expect(manhattanDistance(ship)).toBe(286);
}); 