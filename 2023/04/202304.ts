import { intersection, readInputLines } from '../../src/main/typescript/utils.ts';

type CardId = number;
type MatchCount = number;
type CardPoints = number;

type CardsInfo = Record<CardId, MatchCount>;
type Cache = Record<CardId, number>;

export class ScratchCards {

    private readonly cardsInfo: CardsInfo;
    private readonly cache: Cache;

    constructor() {
      this.cardsInfo = {};
      this.cache = {};
    }

    public addCard(line: string): void {
      const [cardPart, numbersPart] = line.split(':');
      const cardId = this.parseCardId(cardPart);
      const [winningPart, havingPart] = numbersPart.split('|');
      
      const winningNumbers = this.parseNumbers(winningPart);
      const havingNumbers = this.parseNumbers(havingPart);
      const matchingNumbers = intersection(winningNumbers, havingNumbers);
      
      this.cardsInfo[cardId] = matchingNumbers.length;
    }
    
    private parseCardId(cardPart: string): CardId {
      const idMatch = cardPart.match(/Card\s+(\d+)/);
      if (!idMatch) {
        throw new Error(`Invalid card format: ${cardPart}`);
      }
      return parseInt(idMatch[1], 10);
    }
    
    private parseNumbers(numbersString: string): number[] {
      return [...new Set(
        numbersString
          .trim()
          .split(/\s+/)
          .filter(s => s.length > 0)
          .map(s => parseInt(s, 10))
      )];
    }

    private calculateCardPoints(matchCount: MatchCount): CardPoints {
      return matchCount > 0 ? Math.pow(2, matchCount - 1) : 0;
    } 

    public getSumOfCardPoints(): number {
      return Object.values(this.cardsInfo)
        .reduce((sum, matchCount) => sum + this.calculateCardPoints(matchCount), 0);
    }

    private calculateTotalCards(cardId: CardId): number {
      if (cardId in this.cache) {
        return this.cache[cardId];
      }

      const matchCount = this.cardsInfo[cardId];
      let totalCards = 1;
      
      if (matchCount > 0) {
        for (let offset = 1; offset <= matchCount; offset++) {
          const nextCardId = cardId + offset;
          if (nextCardId in this.cardsInfo) {
            totalCards += this.calculateTotalCards(nextCardId);
          }
        }
      }

      this.cache[cardId] = totalCards;
      return totalCards;
    }

    public getSumOfWinningCards(): number {
      return Object.keys(this.cardsInfo)
        .map(id => parseInt(id, 10))
        .reduce((sum, cardId) => sum + this.calculateTotalCards(cardId), 0);
    }
    
}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const scratchCards = new ScratchCards();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        scratchCards.addCard(line);
    }
    
    const part1Result = scratchCards.getSumOfCardPoints();
    console.log(`Step 1: ${part1Result}`);

    const part2Result = scratchCards.getSumOfWinningCards();
    console.log(`Step 2: ${part2Result}`);

  } catch (error) {
    console.error("💥", error);
  }
}



