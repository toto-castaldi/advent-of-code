import { readInputLines } from '../../src/main/typescript/utils.ts';

export class Dial {
    public debug = false;
    zeroPassesCount : number = 0;
    index : number = 50;

    move(instruction : string): number {
        const step = parseInt(instruction.substring(1));

        if (this.debug) console.log(step);

        if (instruction[0] === 'L') this.index -= step; else this.index += step;

        if (this.debug) console.log(this.index);

        if (this.index < 0) this.index = 100 + this.index;
        else if (this.index > 99) this.index =  this.index - 100;

        if (this.index == 0) this.zeroPassesCount ++;

        return this.index;
    }

    getZeroPassesCount(): number {
        return this.zeroPassesCount;
    }

}

if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const dial = new Dial();

        for await (const line of readInputLines(inputPath)) {
            dial.move(line.trim());
        }
        console.log(`${dial.getZeroPassesCount()}`);

    } catch (error) {
        console.error("💥", error);
    }
}