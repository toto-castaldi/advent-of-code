const path = (text) => text.trim().split("\n").map(line => { return { instruction: line.trim().substring(0, 1), instructionValue: parseInt(line.trim().substring(1, line.trim().length)) } });

const manhattanDistance = (ship) => Math.abs(ship.x) + Math.abs(ship.y);

const dirs = ["E", "N", "W", "S"];

const step = (_ship, _step) => {
    const ship = Object.assign({}, _ship);
    const step = Object.assign({}, _step);
    if (step.instruction === "F") {
        step.instruction = ship.dir;
    }
    switch (step.instruction) {
        case 'L':
            ship.dir = dirs[(dirs.indexOf(ship.dir) + step.instructionValue / 90) % dirs.length];
            break;
        case 'R':
            ship.dir = dirs[(dirs.indexOf(ship.dir) + dirs.length - step.instructionValue / 90) % dirs.length];
            break;
        case 'N':
            ship.y += step.instructionValue;
            break;
        case 'S':
            ship.y -= step.instructionValue;
            break;
        case 'W':
            ship.x -= step.instructionValue;
            break;
        case 'E':
            ship.x += step.instructionValue;
            break;
    }
    return ship;
}

const stepTowardsWayPoint = (_ship, _wayPoint, step) => {
    const ship = Object.assign({}, _ship);
    const wayPoint = Object.assign({}, _wayPoint);
    if (step.instruction === "L") {
        step.instruction = "R";
        if (step.instructionValue === 90) step.instructionValue = 270;
        else if (step.instructionValue === 270) step.instructionValue = 90;
    }
    switch (step.instruction) {
        case 'N':
            wayPoint.y += step.instructionValue;
            break;
        case 'S':
            wayPoint.y -= step.instructionValue;
            break;
        case 'W':
            wayPoint.x -= step.instructionValue;
            break;
        case 'E':
            wayPoint.x += step.instructionValue;
            break;
        case 'R':
            if (step.instructionValue === 90) {
                [wayPoint.x, wayPoint.y] = [wayPoint.y, wayPoint.x];
                wayPoint.y *= -1;
            }
            if (step.instructionValue === 180) {
                wayPoint.x *= -1;
                wayPoint.y *= -1;
            }
            if (step.instructionValue === 270) {
                [wayPoint.x, wayPoint.y] = [wayPoint.y, wayPoint.x];
                wayPoint.x *= -1;
            }
            break;
        case 'F':
            ship.x += step.instructionValue * wayPoint.x;
            ship.y += step.instructionValue * wayPoint.y;
            break;
    }
    return { ship, wayPoint }
}


module.exports = { path, manhattanDistance, step, stepTowardsWayPoint }