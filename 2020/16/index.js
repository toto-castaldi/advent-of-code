const { rules, invalid } = require("./ticket");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");
    const rs = rules(data);
    
    const invs = invalid(rs);
    
    const tser = invs.reduce((p,c,i) => p + c.reduce((p,c,i) => p + c, 0), 0);

    console.log(`${tser}`); 
})();
