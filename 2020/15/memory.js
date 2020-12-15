const newMatch = (text) => {
    const sequence = text.trim().split(",").map(num => parseInt(num));
    const match = {
        lastNum: sequence[sequence.length - 1],
        refs: {},
        seqLength: sequence.length
    }
    for (let t = 1; t <= sequence.length; t++) {
        setRef(match, sequence[t - 1], t);
    }
    return match;
}

const setRef = (match, number, t) => {
    match.refs[number] = match.refs[number] || { first: 0, second: 0 };
    match.refs[number].first = match.refs[number].second;
    match.refs[number].second = t;
}

const turn = (match, t) => {
    let last = match.lastNum;
    let diff = 0;
    if (match.refs[last].first !== 0) {
        diff = match.refs[last].second - match.refs[last].first;
    }
    match.lastNum = diff;
    setRef(match, diff, t);
    return diff;
}

const run = (starting, times) => {
    let match = newMatch(starting);
    let res;
    for (let i = match.seqLength + 1; i <= times; i++) {
        res = turn(match, i);
    }
    return res;
}

module.exports = {
    newMatch,
    turn,
    setRef,
    run
}