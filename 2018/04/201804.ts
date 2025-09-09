import { readInputLines } from '../../src/main/typescript/utils.ts';

enum EntryType {
  shift,
  wakeup,
  falls,
}

type GuardLogEntry = {
  date : Date,
  action : EntryType,
  guardId : number | null
}

export class GuardLog {
  private entries : Array<GuardLogEntry> = [];
  public debug : boolean = false;
  private compact : Record<number, Record<number, number>> = {};

  end() {
    this.entries.sort((a,b) => a.date.getDate() - b.date.getDate());
    let guardId : number | null = null;
    let wakeMinutes : number | null = 0;
    this.entries.forEach(e => {
      if (e.action === EntryType.shift) {
        guardId = e.guardId;
        if (this.debug) console.log(`${e.date} : SHIFT ${e.guardId}`);
      } else if (e.action === EntryType.falls) {
        wakeMinutes = e.date.getMinutes();
        if (this.debug) console.log(`${e.date} : FALL -> ${wakeMinutes}`);
      } else if (e.action === EntryType.wakeup) {
        const fm = e.date.getMinutes();
        if (this.debug) console.log(`${e.date} : WAKE -> ${fm}`);
        for (let m = wakeMinutes!; m < fm; m++) {
          if (this.debug) console.log(`Guard ${guardId} . Add minute ${m}`);
          let guardMinutes : Record<number, number> = {};
          if (this.compact[guardId!]) guardMinutes = this.compact[guardId!];
          guardMinutes[m] = (guardMinutes[m] ?? 0) + 1;
          this.compact[guardId!] = guardMinutes;
        }
      }
    });
  }
    
  guardMostSleepedMinuteOfGuard(guardId: number): number {
    const minutes : Record<number, number> = this.compact[guardId];
    let count = 0;
    let result = 0;
    for (const mStr in minutes) {
      const m = Number(mStr);
      const c = minutes[m];
      if (this.debug) console.log(`guard ${guardId} . Minute ${mStr} Count ${c}`);
      if (c > count) {
        result = m;
        count = c;
      }
    }
    return result;
  }

  guardIdWithMostSleepOnMinute(): number {
    let maxGuardId : number | null = null;
    let maxMinutes : number = 0;
    for (const gStr in this.compact) {
      const m = this.compact[gStr];
      const guardId = Number(gStr);
      const sum = Object.values(m).reduce((prev, current) => {
        return prev + current;
      }, 0);
      if (this.debug) console.log(`Guard ${gStr} with ${sum} minutes`);
      if (sum > maxMinutes) {
        maxMinutes = sum;
        maxGuardId = guardId;
      }
    };
    return maxGuardId!;
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