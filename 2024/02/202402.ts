import { readInputLines } from '../../src/main/typescript/utils.ts';

type Report = {
    levels : Array<number>
}

export class LevelReports {
    
    public debug = false;
    private readonly reports: Report[] = [];
    
    
    countSafe(): number {
        let count = 0;
        
        this.reports.forEach(report => {
            let result = true;
            const direction = report.levels[0] - report.levels[1];
            if (direction === 0) {
                result = false;
            } else {
                for (let index = 0; index < report.levels.length - 1; index ++) {
                    const diff = report.levels[index] - report.levels[index + 1];
                    if (Math.sign(diff) !== Math.sign(direction)) {
                        result = false;
                    } else {
                        if (Math.abs(diff) === 0 || Math.abs(diff) > 3) {
                            result = false;
                        }
                    }
                }    
            }

            if (result) count ++;
        });

        return count;
    }

    add(line: string) : void {
        this.reports.push({levels : line.trim().split(/\s+/).map(Number)});
    }

    constructor() {

    }
    
}


if (import.meta.main) {
    try {
        const currentDir = new URL('.', import.meta.url).pathname;
        const inputPath = `${currentDir}input.txt`;

        const locationIdList = new LevelReports();

        for await (const line of readInputLines(inputPath)) {
            locationIdList.add(line);
        }
        
        console.log(`Step 1: ${locationIdList.countSafe()}`);


    } catch (error) {
        console.error("💥", error);
    }
}