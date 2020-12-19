const { group, anyone, everyone } = require("./group");
const fs = require("fs");
const path = require("path");


fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf8", function (err, data) {
    if (err) {
        return console.log(err);
    }

    let lines = data.split("\n");
    lines.push("\n");
    let groupLines = "";

    const groups = [];

    for (line of lines) {
        if (line.trim().length === 0) {
            groups.push(group(groupLines));
            groupLines = "";
        } else {
            groupLines += "\n" + line.trim();
        }
    }

    const sumAllAnyone = groups.reduce((prev, current, index) =>  prev + anyone(current) , 0);

    console.log(`the sum of those counts (Anyone) is ${sumAllAnyone}`);

    const sumAllEveryone = groups.reduce((prev, current, index) =>  prev + everyone(current) , 0);

    console.log(`the sum of those counts (Everyone) is ${sumAllEveryone}`);
});