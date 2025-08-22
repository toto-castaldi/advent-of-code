import { intersection, readInputLines } from '../../src/main/typescript/utils.ts';

type CardsInfo = {
  [id: number]: number;
};

export class ScrathCards {

    private cardsInfo : CardsInfo;

    constructor() {
      this.cardsInfo = {};
    }

    public addCard(line: string) : void {
      const idNumbers = line.split(":").map(s => s.trim());
      const numberLines = idNumbers[1].split("|");
      const id : number = parseInt(idNumbers[0].split("Card ")[1].trim());
      const winningNumbers = [...new Set(numberLines[0].trim().split(" ").filter(s=> s.trim().length > 0).map(s => parseInt(s.trim())))];
      const havingNumbers = [...new Set(numberLines[1].trim().split(" ").filter(s=> s.trim().length > 0).map((s) => parseInt(s.trim())))];

      const i = intersection(winningNumbers, havingNumbers);

      //console.log(line, winningNumbers, havingNumbers, i);

      this.cardsInfo[id] = i.length;
      
    }

    private cardPoints( winningNumberCounts : number) : number {
      return winningNumberCounts > 0 ? Math.pow(2, winningNumberCounts - 1) : 0;
    } 

    public getSumOfCardPoints() : number {
      return Object.values(this.cardsInfo).reduce((acc, val) => {
        return acc + this.cardPoints(val);
      }, 0);
    }

    public getSumOfCardPointsWithBonus() : number {
      return Object.entries(this.cardsInfo).reduce((acc, [id, val]) => {
        let s = acc + val;
        if (val > 0) {
          console.log(`points for Card ${id}`);  
          for (let index = 0; index < val; index ++) {
            const i = parseInt(id) + index + 1;
            const p = this.cardPoints(parseInt(id) + index + 1);
            console.log(`adding points for Card ${i}`);
            s += p;
          } 
        };
        return s;
      }, 0);
    }
    
}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const engineSchema = new ScrathCards();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        engineSchema.addCard(line);
    }
    
    const result = engineSchema.getSumOfCardPoints(); 
    console.log(`Step 1: ${result}`);

  } catch (error) {
    console.error("💥", error);
  }
}



