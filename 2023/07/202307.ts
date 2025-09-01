import { readInputLines } from '../../src/main/typescript/utils.ts';

export enum HandType {
    HIGH_CARD,     
    ONE_PAIR,      
    TWO_PAIR,      
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OR_A_KIND,
    FIVE_OF_A_KIND,
}

type Hand = {
    cards: string;
    bid: number;
    originalHT: HandType;
    bestVersionHT: HandType;
}

const CARD_STRENGTH_ORIGINAL = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'];
const CARD_STRENGTH_JOKER = ['J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A']

export class CamelPoker {
    public debug = false;
    private readonly hands: Hand[] = [];

    constructor() {}

    getSumOfOriginalPoints(): number {
        return this.calculateSum(hand => hand.originalHT, CARD_STRENGTH_ORIGINAL);
    }

    getSumOfBestVersionPoints(): number {
        return this.calculateSum(hand => hand.bestVersionHT, CARD_STRENGTH_JOKER);
    }

    private calculateSum(extractHT: (hand: Hand) => HandType, cardOrder: string[]): number {
        const sortedHands = this.getOrderdHands(extractHT, cardOrder);
        return sortedHands.reduce((sum, hand, index) => sum + (index + 1) * hand.bid, 0);
    }

    private getOrderdHands(extractHT: (hand: Hand) => HandType, cardOrder: string[]): Hand[] {
        this.hands.sort((a, b) => {
            const typeDiff = extractHT(a) - extractHT(b);
            if (typeDiff !== 0) return typeDiff;
            
            return this.compareCards(a.cards, b.cards, cardOrder);
        });
        
        if (this.debug) {
            this.hands.forEach(h => {
                console.log(`${h.cards} | original ${HandType[h.originalHT]} | best ${HandType[h.bestVersionHT]}`);
            });
        }
        return this.hands;
    }

    private compareCards(a: string, b: string, cardOrder: string[]): number {
        for (let i = 0; i < a.length; i++) {
            if (a[i] !== b[i]) {
                if (this.debug) {
                    console.log(`${i} ${a} vs ${b} -> ${a[i]} vs ${b[i]} -> ${cardOrder.indexOf(a[i])} vs ${cardOrder.indexOf(b[i])}`);
                }
                return cardOrder.indexOf(a[i]) - cardOrder.indexOf(b[i]);
            }
        }
        return 0;
    }

    addHand(line: string): void {
        const [cards, bidStr] = line.trim().split(" ");
        const originalHT = this.computeHandType(cards);
        const bestVersionHT = this.computeBestVersion(cards, originalHT);
        
        this.hands.push({ 
            cards, 
            bid: parseInt(bidStr), 
            originalHT, 
            bestVersionHT 
        });
    }

    public computeBestVersion(cards: string, originalHT: HandType): HandType {
        if (!cards.includes('J')) return originalHT;
        
        const uniqueCards = [...new Set(cards.split('').filter(c => c !== 'J'))];
        let bestHT = originalHT;
        
        for (const replacement of uniqueCards) {
            const newCards = cards.replaceAll('J', replacement);
            const candidateHT = this.computeHandType(newCards);
            
            if (this.debug) {
                console.log(`${cards} -> ${newCards}. From ${HandType[bestHT]} to ${HandType[candidateHT]}`);
            }
            
            if (candidateHT > bestHT) {
                bestHT = candidateHT;
            }
        }
        
        return bestHT;
    }

    private computeHandType(cards: string): HandType {
        const counts = this.getCardCounts(cards);
        const frequencies = Object.values(counts).sort((a, b) => b - a);
        
        if (frequencies[0] === 5) return HandType.FIVE_OF_A_KIND;
        if (frequencies[0] === 4) return HandType.FOUR_OR_A_KIND;
        if (frequencies[0] === 3 && frequencies[1] === 2) return HandType.FULL_HOUSE;
        if (frequencies[0] === 3) return HandType.THREE_OF_A_KIND;
        if (frequencies[0] === 2 && frequencies[1] === 2) return HandType.TWO_PAIR;
        if (frequencies[0] === 2) return HandType.ONE_PAIR;
        return HandType.HIGH_CARD;
    }

    private getCardCounts(cards: string): Record<string, number> {
        const counts: Record<string, number> = {};
        for (const card of cards) {
            counts[card] = (counts[card] || 0) + 1;
        }
        return counts;
    }
}

if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const camelPoker1 = new CamelPoker();
        for await (const line of readInputLines(inputPath)) {
            camelPoker1.addHand(line);
        }
        console.log(`Step 1: ${camelPoker1.getSumOfOriginalPoints()}`);

        const camelPoker2 = new CamelPoker();
        for await (const line of readInputLines(inputPath)) {
            camelPoker2.addHand(line);
        }
        console.log(`Step 2: ${camelPoker2.getSumOfBestVersionPoints()}`);
    } catch (error) {
        console.error("💥", error);
    }
}