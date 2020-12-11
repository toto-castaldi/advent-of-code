const adaptersBag = (text) => text.trim().split("\n").map(line => parseInt(line.trim()));

const arrangement = (seatJolt, bag) => {
    const sequence = [...bag].sort((a, b) => a - b);
    const distribution = {};
    let currentJoltLevel = seatJolt;
    let device = 0;
    for (adapter of sequence) {
        //console.debug(adapter);
        let diff = adapter - currentJoltLevel;
        currentJoltLevel = adapter;
        if (distribution[diff] === undefined) distribution[diff] = 0;
        distribution[diff]++;
        device = adapter + 3;
    }
    distribution["3"]++;

    return { distribution, device, seatJolt, sequence };
}

const arrangementsR = (abc) => {
    let result = [abc];
    let sequence = [...abc];
    for (let i = 0; i < sequence.length; i++) {
        if (sequence[i + 1] - sequence[i - 1] < 3) {
            let newSeq = [...sequence];
            newSeq.splice(i, 1);
            result = result.concat(arrangements(newSeq));
        }
    }
    return result;
}

const arrangements = (sequence) => {
    let step = 1;
    let found = true;
    let optionals = [];
    while (found) {
        let initialOptionalCount = optionals.length;
        for (let i = 0; i < sequence.length; i++) {
            if (sequence[i + step] - sequence[i - 1] <= 3) {
                optionals.push(sequence.slice(i, i + step));
            }
        }
        step ++;
        found = initialOptionalCount !== optionals.length;
    }
    //console.log(sequence);
    console.log(optionals);
    let combinations = [];
    for(let i = 0; i < optionals.length; i++) {
        let base = optionals[i];
        combinations.push(base);
        for (let len = 1; len < optionals.length; len++) {
            for (let j = i + 1; j <= optionals.length - len; j++) {
                let added = optionals.slice(j, j + len).reduce((p,c) => p.concat(c), []);
                combinations.push(base.concat(added));
            }
        }
        
    }
    //console.log(combinations);
    let a = new Set();
    for (let comb of combinations) {
        let uniq = [...new Set(comb)];
        uniq.sort();
        a.add(uniq.join("-"));
    }
    //console.table(combinations);
    console.log(Math.pow(2,14));
    return a;
}

module.exports = { arrangement, adaptersBag, arrangements }