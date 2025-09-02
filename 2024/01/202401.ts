import { readInputLines } from '../../src/main/typescript/utils.ts';

export class LocationIdList {
    public debug = false;
    private readonly leftList: number[] = [];
    private readonly rightList: number[] = [];

    getSumOfDistances(): number {
        const sortedLeft = [...this.leftList].sort((a, b) => a - b);
        const sortedRight = [...this.rightList].sort((a, b) => a - b);
        
        if (this.debug) {
            console.log('Sorted left:', sortedLeft);
            console.log('Sorted right:', sortedRight);
        }
        
        return sortedLeft.reduce((sum, leftValue, index) => sum + Math.abs(leftValue - sortedRight[index]), 0);
    }

    getSumOfSimilarities(): number {
        const frequencyMap = new Map<number, number>();
        
        for (const value of this.rightList) {
            frequencyMap.set(value, (frequencyMap.get(value) ?? 0) + 1);
        }
        
        if (this.debug) {
            console.log('Frequency map:', Object.fromEntries(frequencyMap));
        }
        
        return this.leftList.reduce((sum, value) => {
            const frequency = frequencyMap.get(value) ?? 0;
            const similarity = value * frequency;
            
            if (this.debug) {
                console.log(`Value ${value}: frequency ${frequency}, similarity ${similarity}`);
            }
            
            return sum + similarity;
        }, 0);
    }

    add(left: number, right: number): void {
        this.leftList.push(left);
        this.rightList.push(right);
    }
}

if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const locationIdList = new LocationIdList();

        for await (const line of readInputLines(inputPath)) {
            const [left, right] = line.trim().split(/\s+/).map(Number);
            locationIdList.add(left, right);
        }
        console.log(`Step 1: ${locationIdList.getSumOfDistances()}`);

        console.log(`Step 2: ${locationIdList.getSumOfSimilarities()}`);

    } catch (error) {
        console.error("💥", error);
    }
}