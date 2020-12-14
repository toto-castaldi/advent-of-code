const program = (text) => text.trim().split("\n").map(line => {
    const [inst, param] = line.trim().split(" = ");
    if (inst === "mask") {
        return { mask: param.trim() }
    } else {
        return { mem: parseInt(inst.substring(4, inst.length - 1)), value: parseInt(param.trim()) }
    }
});

const binToDec = (bin) => bin.split("").reduceRight((p, c, i) => p + c * Math.pow(2, bin.length - i - 1), 0)

const applyMask = (mask, value) => {
    const valueBin = Number(value).toString(2).split("");
    while (valueBin.length < mask.length) valueBin.unshift("0");
    for (let i = 0; i < mask.length; i++) {
        if (mask[i] === "0") valueBin[i] = 0;
        else if (mask[i] === "1") valueBin[i] = 1;
    }

    return binToDec(valueBin.join(""));
}

const run = (prg) => {
    const mem = {};
    let lastMask = undefined;
    for (line of prg) {
        if (line.mask) {
            lastMask = line.mask;
        } else {
            mem[line.mem] = lastMask ? applyMask(lastMask, line.value) : line.value;
        }
    }
    return mem;
}

module.exports = {
    program,
    run,
    applyMask,
    binToDec
}