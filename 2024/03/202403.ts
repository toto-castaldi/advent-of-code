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
    

    public debug = false;
    private matches : Match[]  = []; 
    
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

      this.matches.push(...matches);
      
    }

    sumOfAllMultiplication(): number {
      return this.matches.filter(m => m.matchType === MatchType.mul).map(m => m.x! * m.y!).reduce((prev, current) => prev + current, 0);
    }

    sumOfEnabledMultiplication() : number {
      let result : number= 0;
      let enabled : boolean = true;

      this.matches.forEach(m => {
        if (this.debug) {
          console.log(`enabled ${enabled} : X = ${m.x} * Y = ${m.y}`);
        }
        if (m.matchType === MatchType.do) {
          enabled = true;
        } else if (m.matchType === MatchType.dont) {
          enabled = false;
        } else {
          if (enabled) {
            result += m.x! * m.y!;
          }
        }
      });

      return result;
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