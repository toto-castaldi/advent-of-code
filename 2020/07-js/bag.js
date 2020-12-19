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

const contains = (rules, bag) => {
    let result = [];
    for (let rule of rules) {
        if (rule.childs.filter(child => child.name === bag).length > 0) {
            result.push(rule);
            result = result.concat(contains(rules, rule.name));
        }
    }

    return [...new Set(result)];
}

const navigate = (rules, bag) => {
    let result = 0;
    for (let rule of rules) {
        if (rule.name === bag) {
            result ++;
            for (let child of rule.childs) {
                result += child.count * navigate(rules, child.name);
            }
        }
    }
    return result;
}

module.exports = {
    rules,
    contains,
    navigate
}