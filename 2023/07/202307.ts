import { readInputLines, reverseEnumIteration, coupleCount } from '../../src/main/typescript/utils.ts';

enum HandType {
    HIGH_CARD,     
    ONE_PAIR,      
    TWO_PAIR,      
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OR_A_KIND,
    FIVE_OF_A_KIND,
}

type Hand = {
    cards : string,
    bid : number,
    originalHT : HandType
    bestVersionHT : HandType,
}

export class CamelPoker {
    
    public debug : boolean = false;
    private readonly hands : Array<Hand>;

    constructor () {
        this.hands = new Array<Hand>();
    }

    getSumOfOriginalPoints() : number {
      let result : number = 0;
      const arr = this.getOrderdHands((hand : Hand) => hand.originalHT, ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']);
      for (const index in arr) {
        result += (parseInt(index) + 1) * arr[index].bid;
      }
      return result;
    }

    getSumOfBestVersionPoints(): number {
      let result : number = 0;
      const arr = this.getOrderdHands((hand : Hand) => hand.bestVersionHT, ['J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A']);
      for (const index in arr) {
        result += (parseInt(index) + 1) * arr[index].bid;
      }
      return result;
    }

    getOrderdHands(extractHT: (hand: Hand) => HandType, cardOrder: string[]) : Array<Hand> {
      this.hands.sort((a, b) => {
        const aHT : HandType = extractHT(a);
        const bHT : HandType = extractHT(b);
        if (aHT === bHT) {
          let index : number = 0;
          while (a.cards[index] === b.cards[index] && index < a.cards.length && index < b.cards.length ) index ++;
          if (index < a.cards.length && index < b.cards.length)  {
            const charA = a.cards[index];
            const charB = b.cards[index];
            if (this.debug) console.log(`${index} ${a.cards} vs ${b.cards} -> ${charA} vs ${charB} -> ${cardOrder.indexOf(charA)} vs ${cardOrder.indexOf(charB)}`);
            return cardOrder.indexOf(charA) - cardOrder.indexOf(charB);
          }
          else
            return 0;
        } else {
          return aHT - bHT;
        }
      });
      return this.hands;
    }

    addHand(line: string) : void {
      const [original, bidStr] = line.trim().split(" ").map( s => s.trim());
      const originalHT : HandType = this.computeHandType(original);
      this.hands.push({ cards: original, bid : parseInt(bidStr), originalHT , bestVersionHT : this.computeBestVersion(original, originalHT)});
    }

    computeBestVersion(cards: string, originalHT : HandType) : HandType  {
      if (cards.includes('J')) {
        let bestHT : HandType = originalHT;
        for (const card of cards.split('')) {
          if (card !== 'J') {
            const newCard = card.replaceAll('J', card);
            const opponentHT = this.computeHandType(newCard);
            if (opponentHT > bestHT) {
              bestHT = opponentHT;
            }
          }
        }
        return bestHT;
      } else {
        return originalHT;
      }
    }

    computeHandType(cards:string) : HandType {
      const rev = reverseEnumIteration(HandType);
      
      for (const hType of rev) {
        switch (hType[1]) {
          case HandType.FIVE_OF_A_KIND : if (this.isFiveOfAKind(cards)) return HandType.FIVE_OF_A_KIND; break;
          case HandType.FOUR_OR_A_KIND : if (this.isFourOfAKind(cards)) return HandType.FOUR_OR_A_KIND; break;
          case HandType.FULL_HOUSE : if (this.isFullHouse(cards)) return HandType.FULL_HOUSE; break;
          case HandType.THREE_OF_A_KIND : if (this.isThreeOfAKind(cards)) return HandType.THREE_OF_A_KIND; break;
          case HandType.TWO_PAIR : if (coupleCount(cards) == 2) return HandType.TWO_PAIR; break;
          case HandType.ONE_PAIR : if (coupleCount(cards) == 1) return HandType.ONE_PAIR; break;
          default : break;
        }
      };
      return HandType.HIGH_CARD;
    }
    
    isFiveOfAKind(cards: string) : boolean{
      const chars = cards.split('').sort();
      return chars[0] === chars[chars.length - 1];
    }

    isFourOfAKind(cards: string) : boolean{
      const chars = cards.split('').sort();
      return chars[0] === chars[chars.length - 2] || chars[1] === chars[chars.length - 1];
    }

    isFullHouse(cards: string) : boolean{
      const chars = cards.split('').sort();
      if (chars[0] === chars[1] && chars[2] === chars[3] && chars[3] === chars[4]) return true;
      if (chars[0] === chars[1] && chars[1] === chars[2] && chars[3] === chars[4]) return true;
      return false;
    }

    isThreeOfAKind(cards: string) : boolean{
      const chars = cards.split('').sort();
      if (chars[0] === chars[1] && chars[1] === chars[2]) return true;
      if (chars[1] === chars[2] && chars[2] === chars[3]) return true;
      if (chars[2] === chars[3] && chars[3] === chars[4]) return true;
      return false;
    }

}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const camelPoker = new CamelPoker();

    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        camelPoker.addHand(line);
    }
    
    const part1Result = camelPoker.getSumOfOriginalPoints();
    console.log(`Step 1: ${part1Result}`);

    const part2Result = camelPoker.getSumOfBestVersionPoints();
    console.log(`Step 2: ${part2Result}`);
  } catch (error) {
    console.error("💥", error);
  }
}