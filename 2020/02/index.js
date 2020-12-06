const fs = require('fs');

const { isValidPartOne, isValidPartTwo } = require('./password.js');

fs.readFile('./input.txt', 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split('\n');
    let passwords = [];

    for (line of lines) {
        passwords.push(line);
    }
   

    console.log(`${passwords.filter(password => isValidPartOne(password)).length} passwords are valid first method`);
    console.log(`${passwords.filter(password => isValidPartTwo(password)).length} passwords are valid second method`);
});