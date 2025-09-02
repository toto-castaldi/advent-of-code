import { numbers, readInputLines } from '../../src/main/typescript/utils.ts';


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

    getSumOfSimilarities(): number {
        const similarities : Record<number, number> = {};
        this.rigth.forEach(r => {
            if (r in similarities) {
                similarities[r] += 1;
            } else {
                similarities[r] = 1;
            }
        });
        if (this.debug) console.log(similarities);
        let result = 0;
        this.left.forEach(l => {
            if (this.debug) console.log(l in similarities ? l * similarities[l] : 0);
            result += l in similarities ? l * similarities[l] : 0;
        });
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
            const [l, r] = line.trim().split("   ").map(s => parseInt(s.trim()));
            locationIdList.add(l, r);
        }
        console.log(`Step 1: ${locationIdList.getSumOfDistances()}`);

        console.log(`Step 2: ${locationIdList.getSumOfSimilarities()}`);

    } catch (error) {
        console.error("💥", error);
    }
}