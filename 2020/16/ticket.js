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
    const createTicket = n => n.trim().split(",").map(v => parseInt(v.trim()));
    nearby = nearby.trim().split("\n").map(createTicket);
    return { your: createTicket(your), fields, nearby, valid: [], notValid: [] };
}

const control = (rs) => {
    const invalidFields = [];
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
            invalidFields.push(inValidFields);
            rs.notValid.push(ticket);
        } else {
            rs.valid.push(ticket);
        }

    }
    rs.invalidFields = invalidFields;

    let noTable = [];

    for (let i = 0; i < rs.valid[0].length; i++) {
        const row = [];
        for (let j = 0; j < rs.fields.length; j++) {
            const rule = rs.fields[j].range;
            let oneInvalid = false;
            for (let k = 0; k < rs.valid.length && !oneInvalid; k++) {
                const validTicket = rs.valid[k];
                const tv = validTicket[i];
                if (tv < rule.min || (tv > rule.iMin && tv < rule.iMax) || tv > rule.max) {
                    oneInvalid = true;
                }
            }
            if (oneInvalid) {
                row.push("X");
            } else {
                row.push(" ");
            }
        }
        noTable.push(row);
    }

    rs.steps = [];
    rs.fieldColumns = {}
    while (Object.keys(rs.fieldColumns).length < rs.fields.length) {
        let rowIndex = undefined;
        let ruleIndex = undefined;
        let noTableCopy = [];
        for (let i = 0; i < noTable.length && rowIndex === undefined; i++) {
            let row = noTable[i];
            const spaces = row.reduce((p, c, i) => { if (c === " ") { p.push(i); } return p; }, []);
            if (spaces.length === 1) {
                rowIndex = i;
                ruleIndex = spaces[0];
                row[spaces[0]] = "0";
                for (let j = 0; j < noTable.length; j++) {
                    if (j !== i) {
                        noTable[j][spaces[0]] = "-";
                        noTableCopy[j] = [...noTable[j]];
                    }
                }
                rs.steps.push(noTableCopy);
                
            }
        }
        if (rowIndex !== undefined) {
            rs.fieldColumns[rs.fields[ruleIndex].name] = rowIndex;
        }

    }

    return rs;
}

module.exports = {
    rules,
    control
}