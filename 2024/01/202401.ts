import { readInputLines } from '../../src/main/typescript/utils.ts';


export class LocationIdList {

    public debug = false;
    private readonly left: number[] = [];
    private readonly rigth: number[] = [];

    constructor() {}

    getSumOfDistances(): number {
        let result = 0;
        const lo = [...this.left].sort();
        const ro = [...this.rigth].sort();
        if (this.debug) {
            console.log(lo);
            console.log(ro);
        }
        for (const index in lo) {
            const l = lo[index];
            const r = ro[index];
            result += Math.abs(l-r);
        }
        return result;
    }


    add(l: number, r: number) : void {
        this.left.push(l);
        this.rigth.push(r);
    }
}

if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const locationIdList = new LocationIdList();
        for await (const line of readInputLines(inputPath)) {
            const [l, r] = line.trim().split(" ").map(s => parseInt(s.trim()));
            locationIdList.add(l, r);
        }
        console.log(`Step 1: ${locationIdList.getSumOfDistances()}`);

    } catch (error) {
        console.error("💥", error);
    }
}