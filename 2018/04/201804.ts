import { readInputLines } from '../../src/main/typescript/utils.ts';

export class GuardLog {
    
  guardMostSleepedMinuteOfGuard(guardId: number): number {
      throw new Error("Method not implemented.");
    }

    guardIdWithMostSleepOnMinute(): number {
      throw new Error("Method not implemented.");
    }

    add(line: string): void {
      throw new Error("Method not implemented.");
    }
    

    public debug = false;

}


if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const aoc = new GuardLog();

        for await (const line of readInputLines(inputPath)) {
            aoc.add(line);
        }

        const guardId : number = aoc.guardIdWithMostSleepOnMinute();
        
        console.log(`Step 1: ${guardId * aoc.guardMostSleepedMinuteOfGuard(guardId)}`);
  
    } catch (error) {
        console.error("💥", error);
    }
}