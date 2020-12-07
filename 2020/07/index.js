const { rules, contains, navigate } = require("./bag");
const fs = require("fs");

fs.readFile("./input.txt", "utf8", function (err, data) {
    if (err) {
        return console.log(err);
    }
    const rs = rules(data);

    console.log(`${contains(rs,"shiny gold").length}`);

    console.log(`${navigate(rs,"shiny gold") -1 }`);
});