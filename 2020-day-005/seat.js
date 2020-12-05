const maxMin = (passport, charF, charB) => {
    let min = 0;
    let max = Math.pow(2, passport.length);
    for (let i = 0; i < passport.length; i++) {
        const c = passport.charAt(i);
        if (c === charF) {
            max = (max + min) / 2;
        } else if (c === charB) {
            min = (max + min) / 2;
        }
    }
    return min;
}

const id = (row, column) => row * 8 + column;

const seat = (passport) => {
    const row = maxMin(passport.substring(0,7), 'F', 'B'); 
    const column = maxMin(passport.substring(7,10), 'L', 'R');
    return { 
        row, 
        column , 
        id: id(row, column)
    };
}

module.exports = {
    seat,
    id
};