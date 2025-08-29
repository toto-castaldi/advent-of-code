type Race = {
  time : number,
  distance : number
}

export class BoatRace {
 
    public debug : boolean = false;
    private races : Array<Race>;

    winningWayCount(index: number): number {
      const race : Race = this.races[index];
      const minimum = this.searchForMinimumHoldTimeWinning(race.time, race.distance);
      if (this.debug) console.log(`for race [${race.time} ${race.distance}] MIN ${minimum}}`);
      const maximum = this.searchForMaximumHoldTimeWinning(race.time, race.distance);
      if (this.debug) console.log(`for race [${race.time} ${race.distance}] MAX ${maximum}}`);
      return maximum - minimum + 1;
    }

    addRace(time: number, distance: number) {
      this.races.push({ time, distance});
    }

    
    constructor() {
      this.races = new Array<Race>();
    }

    private validHoldTime(t : number, time : number, distance : number) : boolean {
      const result = t * (time - t) > distance;
      if (this.debug) console.log(`testing ${t} ${time} ${distance} -> ${result}`)
      return result;
    }

    private searchForMinimumHoldTimeWinning(time: number, distance: number) : number{
      let min = 0;
      while (!this.validHoldTime(min, time, distance) && min < time ) {
        min ++;
      }
      return min;
    }

    private searchForMaximumHoldTimeWinning(time: number, distance: number) : number{
      let max = time;
      while (!this.validHoldTime(max, time, distance) && max > 0 ) {
        max --;
      }
      return max;
    }
    
}



if (import.meta.main) {
  
  try {
    
    let boatRace = new BoatRace();

    boatRace.addRace(55,246);
    boatRace.addRace(82,1441);
    boatRace.addRace(64,1012);
    boatRace.addRace(90,1111);

    const part1Result = boatRace.winningWayCount(0) * boatRace.winningWayCount(1) * boatRace.winningWayCount(2) * boatRace.winningWayCount(3);
    console.log(`Step 1: ${part1Result}`);

    boatRace = new BoatRace();
    boatRace.addRace(55826490,246144110121111);

    const part2Result = boatRace.winningWayCount(0);
    console.log(`Step 2: ${part2Result}`);
  } catch (error) {
    console.error("💥", error);
  }
}
