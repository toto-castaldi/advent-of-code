const rules = (text) => {
    let result = [];
    for (let textRule of text.trim().split("\n")) {
        //light red bags contain 1 bright white bag, 2 muted yellow bags.
        const name = textRule.split(" bags contain ")[0].trim().toLowerCase();
        const childs = [];
        if (textRule.indexOf("contain no other") === -1) {
            for (let child of textRule.split(" bags contain ")[1].trim().toLowerCase().split(",")) {
                child = child.trim().split(" bag")[0];
                childs.push({
                    count: parseInt(child),
                    name: child.substring(parseInt(child).toString().length + 1),
                });
            }
        }
        result.push({
            name,
            childs
        })
    }
    return result;
}

const containsDirectly = (rules, bag) => {
    let result = [];
    for (let rule of rules) {
        result = result.concat(rule.childs.filter(child => child.name === bag).length ? rule : []);
    }
    return [...new Set(result)];
}

const containsIndirectly = (rules, bag) => {
    let result = [];
    for (let parent of containsDirectly(rules, bag)) {
        result = result.concat(containsDirectly(rules, parent.name));
    }
    return [...new Set(result)];
}

module.exports = {
    rules,
    containsDirectly,
    containsIndirectly
}