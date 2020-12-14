String.prototype.replaceAt = function(index, replacement) {
    return this.substr(0, index) + replacement + this.substr(index + replacement.length);
}

const program = (text) => text.trim().split("\n").map(line => {
    const [inst, param] = line.trim().split(" = ");
    if (inst === "mask") {
        return { mask: param.trim() }
    } else {
        return { mem: parseInt(inst.substring(4, inst.length - 1)), value: parseInt(param.trim()) }
    }
});

const binToDec = (bin) => bin.split("").reduceRight((p, c, i) => p + c * Math.pow(2, bin.length - i - 1), 0)

const applyMaskV1 = (mask, value) => {
    let valueBin = Number(value).toString(2);
    while (valueBin.length < mask.length) valueBin = "0" + valueBin;
    for (let i = 0; i < mask.length; i++) {
        if (mask[i] === "0") alueBin = valueBin.replaceAt(i, "0");
        else if (mask[i] === "1") valueBin = valueBin.replaceAt(i, "1");
    }

    return binToDec(valueBin);
}

const applyMaskV2 = (mask, value) => {
    let valueBin = Number(value).toString(2);
    while (valueBin.length < mask.length) valueBin = "0" + valueBin;
    for (let i = 0; i < mask.length; i++) {
        if (mask[i] === "1") valueBin = valueBin.replaceAt(i, "1");
        else if (mask[i] === "X") valueBin = valueBin.replaceAt(i, "X");
    }

    return valueBin
}

const floatings = (mask) => {
    let xIndexes = []; 
    let result = [];
    for (let i = 0; i < mask.length; i++) {
        if (mask.charAt(i) === "X") xIndexes.push(i);
    }
    let combinationCount = Math.pow(2, xIndexes.length);
    for (let i = 0; i < combinationCount; i++) {
        let xCombination = Number(i).toString(2).split("");
        while (xCombination.length < xIndexes.length) xCombination.unshift("0");
        //console.log(xCombination);
        for (let j = 0; j < xCombination.length; j++) {
            mask = mask.replaceAt(xIndexes[j], xCombination[j]);
        }
        result.push(mask);
    }

    return result.map((m) => binToDec(m));
}

const runV2 = (prg) => {
    const mem = {};
    let lastMask = undefined;
    for (line of prg) {
        if (line.mask) {
            lastMask = line.mask;
        } else {
            const mask2 = applyMaskV2 (lastMask, line.mem);
            const floatingMems = floatings(mask2);
            for (fm of floatingMems) {
                mem[fm] = line.value;
            }
        }
    }
    return mem;
}

const runV1 = (prg) => {
    const mem = {};
    let lastMask = undefined;
    for (line of prg) {
        if (line.mask) {
            lastMask = line.mask;
        } else {
            mem[line.mem] = lastMask ? applyMaskV1(lastMask, line.value) : line.value;
        }
    }
    return mem;
}

module.exports = {
    program,
    runV1,
    applyMaskV1,
    applyMaskV2,  
    floatings,
    binToDec,
    runV2
}