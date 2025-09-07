import { readInputLines } from '../../src/main/typescript/utils.ts';

enum MatchType {
  do,
  dont,
  mul
}

interface Match {
  matchType: MatchType;
  position: number;
  length: number;
  match: string;
  x : number | null,
  y : number | null
}

export class MulComputer {
    sumOfEnabledMultiplication() : number {
      throw new Error("Method not implemented.");
    }

    public debug = false;
    private multiplications : Match[]  = []; 
    
    add(line: string) : void {
      const matches: Match[] = [];
  
      const doPattern = /do\(\)/g;
      let match;
      
      while ((match = doPattern.exec(line)) !== null) {
        matches.push({
          matchType: MatchType.do,
          position: match.index,
          length: match[0].length,
          match: match[0],
          x : null,
          y : null
        });
      }
      
      const dontPattern = /don't\(\)/g;
      
      while ((match = dontPattern.exec(line)) !== null) {
        matches.push({
          matchType: MatchType.dont,
          position: match.index,
          length: match[0].length,
          match: match[0],
          x : null,
          y : null
        });
      }
      
      const mulPattern = /mul\((\d+),(\d+)\)/g;
      
      while ((match = mulPattern.exec(line)) !== null) {
        matches.push({
          matchType: MatchType.mul,
          position: match.index,
          length: match[0].length,
          match: match[0],
          x : parseInt(match[1]),
          y : parseInt(match[2])
        });
      }
      
      matches.sort((a, b) => a.position - b.position);

      this.multiplications.push(...matches);
      
    }

    sumOfAllMultiplication(): number {
      return this.multiplications.filter(m => m.matchType === MatchType.mul).map(m => m.x! * m.y!).reduce((prev, current) => prev + current, 0);
    }
    
}


if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const mulComputer = new MulComputer();

        for await (const line of readInputLines(inputPath)) {
            mulComputer.add(line);
        }
        
        console.log(`Step 1: ${mulComputer.sumOfAllMultiplication()}`);

        console.log(`Step 2: ${mulComputer.sumOfEnabledMultiplication()}`);
    } catch (error) {
        console.error("💥", error);
    }
}