import { readInputLines } from '../../src/main/typescript/utils.ts';

enum EntryType {
  shift,
  falls,
  wakeup
}

type GuardLogEntry = {
  date : Date,
  action : EntryType,
  guardId : number | null
}

export class GuardLog {
  private entries : Array<GuardLogEntry> = [];
  public debug : boolean = false;

  end() {
    this.entries.sort((a,b) => a.date.getDate() - b.date.getDate());
    if (this.debug) {
      this.entries.forEach(e => {
        console.log(`${e.date} : ${e.action} -> ${e.guardId}`);
      });
    }
  }
    
  guardMostSleepedMinuteOfGuard(guardId: number): number {
    throw new Error("Method not implemented.");
  }

  guardIdWithMostSleepOnMinute(): number {
    throw new Error("Method not implemented.");
  }

  add(line: string): void {
    const regex = /\[(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})\]/;
    const match = line.match(regex);
    const [_, year, month, day, hour, minute] = match!;
    let entryType : EntryType | null = null;
    let guardId : number | null = null;
    if (line.indexOf("begins shift") > 0) {
      entryType = EntryType.shift;
      const regex = /#(\d+)/;
      const match = line.match(regex);
      guardId = match ? parseInt(match[1]) : null;
    }
    else if (line.indexOf("falls asleep") > 0) entryType = EntryType.falls;
    else if (line.indexOf("wakes up") > 0) entryType = EntryType.wakeup;

    this.entries.push({
        date : new Date(parseInt(year), parseInt(month) - 1, parseInt(day), parseInt(hour), parseInt(minute)),
        action : entryType!,
        guardId
    });

  }

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