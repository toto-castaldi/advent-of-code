const {seat, id} = require('./seat');
const fs = require('fs');
const path = require("path");


const plane = [];

for (let row = 0; row < 128; row++) {
    const planeRow = [];
    for (let column = 0; column < 8; column++) {
        planeRow.push(false);
    }
    plane.push(planeRow);
}

fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }
    const lines = data.split('\n');
    const seats = [];
    for (line of lines) {
        const passenger = seat(line);
        seats.push(passenger);
        plane[passenger.row][passenger.column] = true;
    }



    seats.sort((a, b) => b.id - a.id);
    console.log(`higher id is ${seats[0].id}`);

    const table = [];
    for (let planeRow of plane) {
        table.push(
            planeRow.reduce((prev, seat, index) => { prev[index] = seat; return prev; }, {})
        );
    }

    console.table(table);

    console.log(`empty seat ID is ${id(74,0)}`);
});