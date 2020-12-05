const fs = require('fs');

const { isValidOnRequiredFields, parsePassport, isValid } = require('./passport.js');

fs.readFile('./input.txt', 'utf8', function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split('\n');
    lines.push('\n');
    let passportLines = '';

    let passports = [];

    for (line of lines) {
        if (line.trim().length === 0) {
            passports.push(parsePassport(passportLines));
            passportLines = '';
        } else {
            passportLines += ' ' + line.trim();
        }
    }
   

    console.log(`${passports.filter(passport => isValidOnRequiredFields(passport)).length} passports are valid on num fields`);

    console.log(`${passports.filter(passport => isValid(passport)).length} passports are valid on field values`);
});