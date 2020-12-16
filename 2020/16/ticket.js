const rules = (_input) => {
    const input = _input.replace("your ticket:", "#").replace("nearby tickets:", "#");
    let [fields, your, nearby] = input.trim().split("#");
    fields = fields.trim().split("\n").map(field => {
        const [name, ranges] = field.trim().split(": ");
        const [rangeL, rangeR] = ranges.trim().split(" or ");
        return {
            name, range: {
                min: parseInt(rangeL.split("-")[0]),
                iMin: parseInt(rangeL.split("-")[1]),
                iMax: parseInt(rangeR.split("-")[0]),
                max: parseInt(rangeR.split("-")[1])
            }
        };
    });
    nearby = nearby.trim().split("\n").map(n => n.trim().split(",").map(v => parseInt(v.trim())));
    return { fields, nearby };
}

const invalid = (rs) => {
    const result = [];
    for (let ticket of rs.nearby) {
        let inValidFields = [];
        for (let j = 0; j < ticket.length; j++) {
            let validRule = undefined;
            const tv = ticket[j];
            for (let i = 0; i < rs.fields.length && validRule === undefined; i++) {
                const rule = rs.fields[i].range;
                if ((tv >= rule.min && tv <= rule.iMin) || (tv >= rule.iMax && tv <= rule.max)) {
                    validRule = rule;
                }
            }
            if (validRule === undefined) {
                inValidFields.push(tv);
            }
        }

        if (inValidFields.length > 0) {
            result.push(inValidFields);
        }
    }
    return result;
}

module.exports = {
    rules,
    invalid
}