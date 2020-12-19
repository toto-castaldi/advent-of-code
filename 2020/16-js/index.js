const { rules, control } = require("./ticket");
const fs = require("fs").promises;
const path = require("path");

(async () => {
    const data = await fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8");
    const rs = rules(data);

    const c = control(rs);

    const step1 = c.invalidFields.reduce((p, c, i) => p + c.reduce((p, c, i) => p + c, 0), 0);

    let step2 = 1;

    const logs = [];

    for (const field in c.fieldColumns) {
        if (field.startsWith("departure")) {
            logs.push(`${field} ${c.your[c.fieldColumns[field]]}`);
            step2 *= c.your[c.fieldColumns[field]];
        }
    }

    let index = 0;
    const printStep = () => {
        setTimeout(() => {
            console.clear();
            console.table(rs.steps[index]);
            index++;
            if (index < rs.steps.length) {                
                printStep();
            } else {
                logs.forEach(l => {
                    console.log(l);
                })
                console.log(step1);
                console.log(step2);
            }
        }, 200);
    }

    printStep();
})();
