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
    let index = trx.indexOf(error);
    let weaknessLen = index;
    let sum = -1;
    let startIndex = 0;
    while (weaknessLen > 1 && sum !== error) {
        startIndex = 0;
        while (startIndex <= index - weaknessLen && sum !== error) {
            sum = trx.slice(startIndex, startIndex + weaknessLen).reduce((prev, current, index) => prev + current, 0);
            startIndex++;
        }
        weaknessLen--;
    }
    if (sum === error) {
        startIndex--;
        weaknessLen ++;
        const weakness = trx.slice(startIndex, startIndex + weaknessLen);
        const ordered = [...weakness].sort((a, b) => a - b);
        return { weakness, min: ordered[0], max: ordered[ordered.length - 1] };
    }
}


module.exports = { trasmission, findWrongPacket, findWeakness }