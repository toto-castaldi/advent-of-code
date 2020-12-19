const fs = require("fs");
const path = require("path");

fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split("\n");

    const bad = "😡";
    const good = "😌";
    const normal = "➡";

    const slope = (stepX, stepY) => {
        const rows = [];

        for (line of lines) {
            rows.push(line.split(""));
        }
        let x = 0;
        let y = 0;

        let countBad = 0;

        const step = () => {
            let result = 0;
            if (rows[y][x] === '#') {
                result = 1;
                rows[y][x] = bad;
            } else {
                rows[y][x] = good;
            }
            return result;
        }

        while (y < rows.length) {
            countBad += step();
            for (let i = 0; i < stepX; i++) {
                x++;
                if (x >= rows[0].length) {
                    x -= rows[0].length;
                }
                rows[y][x] = normal;
            }
            y += stepY;

        }

        return { rows , countBad};
    }

    const firstParth = slope(3, 1);
    console.table(firstParth.rows);
    console.log(firstParth.countBad);

    const secondParth1 = slope(1, 1);
    const secondParth3 = slope(5, 1);
    const secondParth4 = slope(7, 1);
    const secondParth5 = slope(1, 2);

    console.log(secondParth1.countBad * firstParth.countBad * secondParth3.countBad * secondParth4.countBad * secondParth5.countBad);

});