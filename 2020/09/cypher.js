const trasmission = (text) => text.trim().split("\n").map(packet => parseInt(packet.trim()));

const addends = (numbers, sum) => {
    numbers.sort((a, b) => a - b);
    let min = 0;
    let max = numbers.length - 1;
    while (min < max && numbers[min] + numbers[max] !== sum) {
        if (numbers[min] + numbers[max] > sum) {
            max--;
        }
        if (numbers[min] + numbers[max] < sum) {
            min++;
        }
    }
    if (min < max) {
        return { a: numbers[min], b: numbers[max] };
    }
    return {};
}

const findWrongPacket = (preambleLen, trx) => {
    let index = preambleLen;
    let error;
    while (index < trx.length && error === undefined) {
        const packet = trx[index];
        if (Object.keys(addends(trx.slice(index - preambleLen, index), packet)).length !== 2) error = packet;
        index++;
    }
    return error;
}

const findWeakness = (error, trx) => {
    let to = trx.indexOf(error);
    let from = to - 1;
    let s = trx[from] + trx[to];

    while (s !== error && from > 0) {
        if (s > error) { 
            s -= trx[to]; 
            to--; 
        } else {
            from--;
            s += trx[from];
        }
    }

    if (s === error) {
        const weakness = trx.slice(from, to);
        const ordered = [...weakness].sort((a, b) => a - b);
        return { weakness, min: ordered[0], max: ordered[ordered.length - 1] };
    }
}


module.exports = { trasmission, findWrongPacket, findWeakness }