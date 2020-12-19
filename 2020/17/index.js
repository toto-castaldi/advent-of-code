const { actives, space, round } = require("./conway");
const fs = require("fs").promises;

(async () => {
    const data = await fs.readFile("./input.txt", "utf8");

    //let _sp = space(data);
    let sp = space(`
    .#.
    ..#
    ###
    `);


    console.log(sp.data);    

    sp = round(sp);

    console.log(sp.data);    

    

  
})();
