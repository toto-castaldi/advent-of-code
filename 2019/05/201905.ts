import { readInputLines } from '../../src/main/typescript/utils.ts';

export class IntCodeComputer {

    load(program: string): IntCodeComputer {
      throw new Error("Method not implemented.");
    }

    execute() : Array<number> {
      throw new Error("Method not implemented.");
    }

    setMem(position : number, value : number) : IntCodeComputer {
      throw new Error("Method not implemented.");
    }
    

    public debug = false;
  
    
}


if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const aoc = new IntCodeComputer();

        for await (const line of readInputLines(inputPath)) {
            aoc.load(line);
        }

        aoc.setMem(1, 12);
        aoc.setMem(2, 2);
        
        console.log(`Step 1: ${aoc.execute()[0]}`);
    } catch (error) {
        console.error("💥", error);
    }
}