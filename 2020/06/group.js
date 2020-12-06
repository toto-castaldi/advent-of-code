const group = (g) => {
    const lines = g.split("\n");
    const answers = {};
    let voterCount = 0;
    for (line of lines) {
        const characters = line.trim().split("");
        if (characters.length > 0) voterCount ++;
        for (character of characters) {
            if (answers[character] === undefined) answers[character] = 1; else answers[character]++;
        }
    }
    return { answers, voterCount };
}

const everyone = (g) =>  {
    let result = 0;
    for (question in g.answers) {
        if (g.answers[question] === g.voterCount) result ++;
    }   
    return result;
} 

const anyone = (g) => Object.keys(g.answers).length;

module.exports = {
    group,
    anyone,
    everyone
}