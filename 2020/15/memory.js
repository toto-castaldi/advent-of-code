const newMatch = (text) => { return { turnNumber: 0, sequence: text.trim().split(",").map(num => parseInt(num)), refs: {} } }

const setRef = (match, number, t) => {
    match.refs[number] = match.refs[number] || { first: 0, second: 0 };
    match.refs[number].first = match.refs[number].second;
    match.refs[number].second = t;
}

const turn = (match) => {
    let n;
    let t = ++match.turnNumber;
    if (t <= match.sequence.length) {
        n = match.sequence[t - 1];
    } else {
        let last = match.sequence[t - 2];
        n = match.refs[last].first == 0 ? 0 : match.refs[last].second - match.refs[last].first;
        match.sequence.push(n);
    }
    setRef(match, n, t);
    return n;
}

const run = (starting, times) => {
    let match = newMatch(starting);
    let t;
    for (let i = 0; i < times; i++) {
        t = turn(match);
    }
    return t;
}

module.exports = {
    newMatch,
    turn,
    setRef,
    run
}