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

const arrangements = (abc) => {
    let result = [abc];
    let sequence = [...abc];
    for (let i = 0; i < sequence.length; i++) {
        if (sequence[i + 1] - sequence[i - 1] < 3) {
            let newSeq =  [...sequence];
            newSeq.splice(i, 1);
            result = result.concat(arrangements(newSeq));
        }
    }
    return result;
}

module.exports = { arrangement, adaptersBag, arrangements }