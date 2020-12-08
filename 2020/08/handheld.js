const program = (text) => {
    let result = [];
    for (let textRule of text.trim().split("\n")) {
        const operation = textRule.trim().split(" ")[0].trim().toLowerCase();
        const argument = parseInt(textRule.trim().split(" ")[1].trim().toLowerCase());
        result.push({
            executed: false,
            operation,
            argument
        });
    }
    return result;
}

const accumulator = (program) => {
    let acc = 0;
    let index = 0;
    while (index < program.length && !program[index].executed) {
        program[index].executed = true;
        switch (program[index].operation) {
            case "nop":
                index += 1;
                break;
            case "acc":
                acc += program[index].argument;
                index += 1;
                break;
            case "jmp":
                index += program[index].argument;
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
        changeIndex ++;
    }
    return {
        program : prg,
        accumulatorValue
    }
}

module.exports = {
    program,
    accumulator,
    fixAndRun
}