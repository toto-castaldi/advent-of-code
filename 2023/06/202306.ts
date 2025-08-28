type Race = {
  time : number,
  distance : number
}

export class BoatRace {
 
    public debug : boolean = false;
    private races : Array<Race>;

    winningWayCount(index: number): number {
      const race : Race = this.races[index];
      const minimum = this.searchForMinimumHoldTimeWinning(0, race.time, race.distance);
      const maximum = this.searchForMaximumHoldTimeWinning(race);
      if (this.debug) console.log(`for race ${race} . ${minimum} -> ${maximum}`);
      return maximum - minimum;
    }

    addRace(time: number, distance: number) {
      this.races.push({ time, distance});
    }

    
    constructor() {
      this.races = new Array<Race>();
    }

    private searchForMinimumHoldTimeWinning(min : number, time: number, distance: number) : number{
      const middle = Math.floor((time - min) / 2);
      const distanceReach = middle * (time - middle);
      if (distanceReach >= distance) {
        const next = this.searchForMinimumHoldTimeWinning(0, middle, distance);
        if (next >= distance) {
          return next;
        } else {
          return middle;
        }
      } else return this.searchForMinimumHoldTimeWinning(0, middle, distance);
    }

    private searchForMaximumHoldTimeWinning(race: Race) : number{
      throw new Error("Function not implemented.");
    }
    
}



if (import.meta.main) {
  
  try {
    
    const boatRace = new BoatRace();

    boatRace.addRace(55,246);
    boatRace.addRace(82,1441);
    boatRace.addRace(64,1012);
    boatRace.addRace(90,1111);

    
    const part1Result = boatRace.winningWayCount(0) * boatRace.winningWayCount(1) * boatRace.winningWayCount(2) * boatRace.winningWayCount(3);
    console.log(`Step 1: ${part1Result}`);


  } catch (error) {
    console.error("💥", error);
  }
}
