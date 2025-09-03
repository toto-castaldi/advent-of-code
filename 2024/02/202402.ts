import { readInputLines } from '../../src/main/typescript/utils.ts';

type Report = {
    levels : Array<number>
}

export class LevelReports {
    countSafeTolerate(): number {
      let count = 0;
        
        this.reports.forEach(report => {
            let valid = this.reportValid(report);
            if (!valid && report.levels.length > 1) {
                for (let i = 0; i < report.levels.length && !valid; i++) {
                    const newReport = { levels : [] as number[] };    
                    for (let j = 0; j < report.levels.length; j++) {
                        if (i !== j) {
                            newReport.levels.push(report.levels[j]);
                        }
                    }
                    valid = this.reportValid(newReport);
                }
            }
            if (valid) count ++;
        });

        return count;
    }
    
    public debug = false;
    private readonly reports: Report[] = [];
    
    
    countSafe(): number {
        let count = 0;
        
        this.reports.forEach(report => {
            if (this.reportValid(report)) count ++;
        });

        return count;
    }

    private reportValid(report: Report) {
      let result = true;
      const direction = report.levels[0] - report.levels[1];
      if (direction === 0) {
        result = false;
      } else {
        for (let index = 0; index < report.levels.length - 1; index++) {
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
      return result;
    }

    add(line: string) : void {
        this.reports.push({levels : line.trim().split(/\s+/).map(Number)});
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

        console.log(`Step 2: ${locationIdList.countSafeTolerate()}`);
    } catch (error) {
        console.error("💥", error);
    }
}