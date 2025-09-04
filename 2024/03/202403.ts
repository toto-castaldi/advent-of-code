import { readInputLines } from '../../src/main/typescript/utils.ts';

export class MulComputer {

    public debug = false;
    private multiplications : Array<{a: number, b: number, result: number}> = []; 
    
    add(line: string) : void {
      const regex = /mul\((\d+),(\d+)\)/g;
      const matches = [...line.matchAll(regex)];
    
      this.multiplications.push(...matches.map(match => ({
        a: parseInt(match[1]),
        b: parseInt(match[2]),
        result: parseInt(match[1]) * parseInt(match[2])
      })));
    }

    sumOfMultiplication(): number {
      return this.multiplications.map(m => m.result).reduce((prev, current) => prev + current, 0);
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
        
        console.log(`Step 1: ${mulComputer.sumOfMultiplication()}`);
    } catch (error) {
        console.error("💥", error);
    }
}