const forceParseInt = (f) => {
    for (c of f) {
        if (!(c >= 0 && c <= 9)) return NaN;
    } 
    return parseInt(f);
}

const left = (f, c) => f.substring(0, f.length - c);

const requiredFields = {
    'byr': (f) => {
        return forceParseInt(f) >= 1920 && forceParseInt(f) <= 2002;
    },
    'iyr': (f) => {
        return forceParseInt(f) >= 2010 && forceParseInt(f) <= 2020;
    },
    'eyr': (f) => {
        return forceParseInt(f) >= 2020 && forceParseInt(f) <= 2030;
    },
    'hgt': (f) => {
        if (f.endsWith('cm') && forceParseInt(left(f,2)) >= 150 && forceParseInt(left(f,2)) <= 193) return true;
        if (f.endsWith('in') && forceParseInt(left(f,2)) >= 59 && forceParseInt(left(f,2)) <= 76) return true;
        return false;
    },
    'hcl': (f) => {
        if (f.charAt(0) === '#') {
            for (let i = 1; i < f.length; i++) {
                let c = f.charAt(i);
                if (!((c >= 0 && c <= 9) || (c >= 'a' && c <= 'f'))) return false;
            }
            return true;
        }
        return false;
    },
    'ecl': (f) => {
        return ['amb','blu','brn','gry','grn','hzl','oth'].includes(f);
    },
    'pid': (f) => {
        if (f.length !== 9) return false;
        for (let i = 1; i < f.length; i++) {
            let c = f.charAt(i);
            if (!(c >= 0 && c <= 9)) return false;
        }
        return true;
    }
};

const parsePassport = (passportLines) => {
    const res = {};
    for (field of passportLines.split(' ')) {
        const splitted = field.trim().split(':');
        if (splitted[0].toLowerCase().length > 0) {
            res[splitted[0].toLowerCase()] = splitted[1].toLowerCase();
        }
    }
    return res;
}

const isValidOnRequiredFields = (passport) => {
    let countValid = 0;
    const requiredFieldNames = Object.keys(requiredFields);
    for (let field of requiredFieldNames) {
        if (Object.keys(passport).includes(field)) countValid++;
    }
    return countValid === requiredFieldNames.length;
}

const isValid = (passport) => {
    if (isValidOnRequiredFields(passport)) {
        let countValid = 0;
        const requiredFieldNames = Object.keys(requiredFields);
        for (let field of requiredFieldNames) {
            if (requiredFields[field](passport[field])) countValid++;
        }
        return countValid === requiredFieldNames.length;

    } else {
        return false;
    }
}

module.exports = {
    isValidOnRequiredFields,
    parsePassport,
    isValid
}