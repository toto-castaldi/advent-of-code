const fs = require('fs');


fs.readFile(`input-1.txt`, 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split('\n');

    console.log(lines.length);

    let lastVar = -1;
	let countIncrease  = 0;
    
    for (line of lines) {
        const lineInt = parseInt(line.trim());
        if (lastVar > -1) {
			if (lastVar < lineInt) {
				countIncrease++
                console.log(lineInt);
			}
		}
		lastVar = lineInt;
    }
    console.log(countIncrease);

    
});