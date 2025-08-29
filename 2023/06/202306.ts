interface Race {
  readonly time: number;
  readonly distance: number;
}

export class BoatRace {
    public debug = false;
    private readonly races: Race[] = [];

    winningWayCount(index: number): number {
        const race = this.races[index];
        if (!race) throw new Error(`Race at index ${index} not found`);
        
        const minimum = this.findMinimumWinningHoldTime(race.time, race.distance);
        const maximum = this.findMaximumWinningHoldTime(race.time, race.distance);
        
        if (this.debug) {
            console.log(`Race [time: ${race.time}, distance: ${race.distance}]`);
            console.log(`  Min hold time: ${minimum}, Max hold time: ${maximum}`);
        }
        
        return maximum - minimum + 1;
    }

    addRace(time: number, distance: number): void {
        this.races.push({ time, distance });
    }


    private isWinningHoldTime(holdTime: number, raceTime: number, recordDistance: number): boolean {
        const travelDistance = holdTime * (raceTime - holdTime);
        return travelDistance > recordDistance;
    }

    private findMinimumWinningHoldTime(raceTime: number, recordDistance: number): number {
        let left = 0;
        let right = Math.floor(raceTime / 2);
        
        while (left < right) {
            const mid = Math.floor((left + right) / 2);
            if (this.isWinningHoldTime(mid, raceTime, recordDistance)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }

    private findMaximumWinningHoldTime(raceTime: number, recordDistance: number): number {
        let left = Math.ceil(raceTime / 2);
        let right = raceTime;
        
        while (left < right) {
            const mid = Math.ceil((left + right + 1) / 2);
            if (this.isWinningHoldTime(mid, raceTime, recordDistance)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    calculateProductOfWinningWays(...indices: number[]): number {
        return indices.reduce((product, index) => product * this.winningWayCount(index), 1);
    }
    
    getRaceCount(): number {
        return this.races.length;
    }
}



function solvePart1(): number {
    const boatRace = new BoatRace();
    boatRace.addRace(55, 246);
    boatRace.addRace(82, 1441);
    boatRace.addRace(64, 1012);
    boatRace.addRace(90, 1111);
    
    return boatRace.calculateProductOfWinningWays(0, 1, 2, 3);
}

function solvePart2(): number {
    const boatRace = new BoatRace();
    boatRace.addRace(55826490, 246144110121111);
    
    return boatRace.winningWayCount(0);
}

if (import.meta.main) {
    try {
        console.log(`Step 1: ${solvePart1()}`);
        console.log(`Step 2: ${solvePart2()}`);
    } catch (error) {
        console.error("💥", error);
    }
}
