const { rules, contains, navigate } = require("./bag");
const fs = require("fs");
const path = require("path");

fs.readFile(`${path.dirname(require.main.filename)}/input.txt`, "utf-8", function (err, data) {
    if (err) {
        return console.log(err);
    }
    const rs = rules(data);

    console.log(`${contains(rs,"shiny gold").length}`);

    console.log(`${navigate(rs,"shiny gold") -1 }`);
});