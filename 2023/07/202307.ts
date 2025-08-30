import { readInputLines, reverseEnumIteration } from '../../src/main/typescript/utils.ts';

enum HandType {
    HIGH_CARD,     
    ONE_PAIR,      
    TWO_PAIR,      
    THREE_OF_A_KINE,
    FULL_HOUSE,
    FOUR_OR_A_KIND,
    FIVE_OF_A_KIND,
}

type Hand = {
    cards : string,
    bid : number,
    handType : HandType
}

export class CamelPoker {
    public debug : boolean = false;
    private readonly hands : Array<Hand>;

    constructor () {
        this.hands = new Array<Hand>();
    }

    getSumOfPoints() : number {
      let result : number = 0;
      const arr = this.getOrderdHands();
      for (const index in arr) {
        result += (parseInt(index) + 1) * arr[index].bid;
      }
      return result;
    }

    getOrderdHands() : Array<Hand> {
      this.hands.sort((a, b) => {
        return 0;
      });
      return this.hands;
    }

    addHand(line: string) : void {
      const [cards, bidStr] = line.trim().split(" ").map( s => s.trim());
      this.hands.push({ cards, bid : parseInt(bidStr), handType :this.computeHandType(cards)});
    }

    computeHandType(cards:string) : HandType {
      const rev = reverseEnumIteration(HandType);
      
      for (const hType of rev) {
        switch (hType[1]) {
          case HandType.FIVE_OF_A_KIND : if (this.isFiveOfAKind(cards)) return HandType.FIVE_OF_A_KIND; break;
          case HandType.FOUR_OR_A_KIND : if (this.isFiveOfAKind(cards)) return HandType.FOUR_OR_A_KIND; break;
          case HandType.FULL_HOUSE : if (this.isFiveOfAKind(cards)) return HandType.FULL_HOUSE; break;
          case HandType.THREE_OF_A_KINE : if (this.isFiveOfAKind(cards)) return HandType.THREE_OF_A_KINE; break;
          case HandType.TWO_PAIR : if (this.isFiveOfAKind(cards)) return HandType.TWO_PAIR; break;
          case HandType.ONE_PAIR : if (this.isFiveOfAKind(cards)) return HandType.ONE_PAIR; break;
          default : break;
        }
      };
      return HandType.HIGH_CARD;
    }
    
    isFiveOfAKind(cards: string) : boolean{
      throw new Error("Method not implemented.");
    }

}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const camelPoker = new CamelPoker();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        camelPoker.addHand(line);
    }
    
    const part1Result = camelPoker.getSumOfPoints();
    console.log(`Step 1: ${part1Result}`);


  } catch (error) {
    console.error("💥", error);
  }
}