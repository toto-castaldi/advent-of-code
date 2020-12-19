const program = (text) => {
    return text.trim().split("\n").map(textRule => {
        const [operation, argument] = textRule.toLowerCase().trim().split(" ");
        return {
            executed: false,
            operation: operation.trim(),
            argument: parseInt(argument.trim())
        };
    });
}

const accumulator = (program) => {
    let acc = 0;
    let index = 0;
    while (index < program.length && !program[index].executed) {
        program[index].executed = true;
        switch (program[index].operation) {
            case "acc":
                acc += program[index].argument;
                index++;
                break;
            case "jmp":
                index += program[index].argument;
                break;
            default:
                index++;
                break;
        }
    }
    return acc;
}

const fixAndRun = (prg) => {
    let accumulatorValue = 0;
    let changeIndex = 0;
    while (!prg[prg.length - 1].executed) {
        prg.map(line => line.executed = false);
        while (prg[changeIndex].operation === 'acc') changeIndex++;
        prg[changeIndex].operation === 'nop' ? prg[changeIndex].operation = 'jmp' : prg[changeIndex].operation = 'nop';
        accumulatorValue = accumulator(prg);
        prg[changeIndex].operation === 'nop' ? prg[changeIndex].operation = 'jmp' : prg[changeIndex].operation = 'nop';
        changeIndex++;
    }
    return {
        program: prg,
        accumulatorValue
    }
}

module.exports = { program, accumulator, fixAndRun }