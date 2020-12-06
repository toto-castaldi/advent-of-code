const rules = (password) => {
    const spaces = password.split(" ");
    const fromTo = spaces[0].split("-");
    const from = parseInt(fromTo[0]);
    const to = parseInt(fromTo[1]);
    let character = spaces[1].split(":")[0];
    character = character.substring(0, character.length);
    const pass = spaces[2];
    return { from, to, character, pass };
}

const isValidPartOne = (password) => {
    let { from, to, character, pass } = rules(password);

    const count = (pass.split(character).length - 1);

    //console.log(pass, character, count, from, to);

    return count >= from && count <= to;
}

const isValidPartTwo = (password) => {
    let { from, to, character, pass } = rules(password);

    const presentFrom = pass.charAt(from - 1) === character;
    const presentTo = pass.charAt(to - 1) === character;

    //console.log(pass, character, presentFrom, presentTo, from, to);

    return presentFrom && !presentTo || !presentFrom && presentTo;
}

module.exports = {
    isValidPartOne,
    isValidPartTwo
}