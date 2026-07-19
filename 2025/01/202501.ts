import { readInputLines } from '../../src/main/typescript/utils.ts';

export class Dial {
    public debug = false;
    zeroPassesCount : number = 0;
    index : number = 50;
    len : number = 100;

    move(instruction : string): number {
        let step = parseInt(instruction.substring(1));

        if (instruction[0] === 'L') step = - step;

        const next = ((this.index + step) % this.len + this.len) % this.len;

        if (this.debug) console.log(`${this.index} , ${step} -> ${next}`);

        this.index = next;

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