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

    private recPoints(cardNumber : number) : number {
      const cardMatchingNumbers = this.cardsInfo[cardNumber];
      let result = 1;
      if (cardMatchingNumbers !== 0) {
        for (let index = 0; index < cardMatchingNumbers; index ++) {
          const bonusCardIndex = cardNumber + index + 1;
          if (bonusCardIndex in this.cardsInfo) {
            result += this.recPoints(bonusCardIndex);
          }
        }
      }
      return result;
    }

    public getSumOfWinningCards() : number {
      let result = 0;
      for (const cardNumber of Object.keys(this.cardsInfo)) {
        result += this.recPoints(parseInt(cardNumber));
      }
      return result;
    }
    
}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const engineSchema = new ScrathCards();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        engineSchema.addCard(line);
    }
    
    let result = engineSchema.getSumOfCardPoints(); 
    console.log(`Step 1: ${result}`);

    result = engineSchema.getSumOfWinningCards(); 
    console.log(`Step 2: ${result}`);

  } catch (error) {
    console.error("💥", error);
  }
}



