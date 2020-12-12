const path = (text) => text.trim().split("\n").map(line => { return { instruction: line.trim().substring(0, 1), instructionValue: parseInt(line.trim().substring(1, line.trim().length)) } });

const navigate = (p) => {
    const ship = {
        x: 0,
        y: 0,
        dir: 0
    }

    for (let step of p) {
        switch (step.instruction) {
            case 'F':
                ship.x += step.instructionValue * Math.cos(ship.dir);
                ship.y += step.instructionValue * Math.sin(ship.dir);
                break;
            case 'L':
                ship.dir += step.instructionValue * (Math.PI / 180);
                break;
            case 'R':
                ship.dir -= step.instructionValue * (Math.PI / 180);
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
    }

    return ship;
}

const manhattanDistance = (ship) => Math.abs(ship.x) + Math.abs(ship.y);


module.exports = { path, navigate, manhattanDistance }