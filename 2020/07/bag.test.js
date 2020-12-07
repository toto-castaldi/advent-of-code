const { rules, containsDirectly, containsIndirectly } = require("./bag");

test("challenge-input-1", () => {
    const rs = rules(`
    light red bags contain 1 bright white bag, 2 muted yellow bags.
    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
    bright white bags contain 1 shiny gold bag.
    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
    faded blue bags contain no other bags.
    dotted black bags contain no other bags.
    `);
    expect(containsDirectly(rs,"shiny gold")).toHaveLength(2);
    expect(containsIndirectly(rs,"shiny gold")).toHaveLength(2);
});

test("bug-hunt", () => {
    const rs = rules(`
    plaid indigo bags contain 4 dull lime bags, 3 faded violet bags.
    `);
    expect(containsDirectly(rs,"faded violet")).toHaveLength(1);
    expect(containsIndirectly(rs,"faded violet")).toHaveLength(0);
});