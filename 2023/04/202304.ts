import { intersection, containsOnlyDotsOrNumbers, readInputLines, findItemInString, isDigit, extractNumberFromString } from '../../src/main/typescript/utils.ts';

export class ScrathCards {

    private sumOfCardPoints : number;

    constructor() {
        this.sumOfCardPoints = 0;
    }

    public addCard(line: string) : void {
        const numberLines = line.split(":")[1].split("|");
        const winningNumbers = [...new Set(numberLines[0].trim().split(" ").map((s) => parseInt(s.trim())))];
        const havingNumbers = [...new Set(numberLines[1].trim().split(" ").map((s) => parseInt(s.trim())))];

        const i = intersection(winningNumbers, havingNumbers);

        if (i.length > 0) {
            this.sumOfCardPoints += Math.pow(2, i.length - 1);
        }

    }

    public getSumOfCardPoints() : number {
        return this.sumOfCardPoints;
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



