const { rules, containsDirectly, containsIndirectly } = require("./bag");
const fs = require("fs");

fs.readFile("./input.txt", "utf8", function (err, data) {
    if (err) {
        return console.log(err);
    }

    const rs = rules(data);

    rs.forEach(r => console.log(r.name, r.childs));

    const cd = containsDirectly(rs,"shiny gold");
    const ci = containsIndirectly(rs,"shiny gold");

    console.log(`${cd.length}`);
    console.log(`${ci.length}`);
    console.log(`${cd.length + ci.length}`);

    console.log(ci.map(c => c.name));
    console.log(cd.map(c => c.name));

});