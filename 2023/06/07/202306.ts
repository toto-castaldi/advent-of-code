export class BoatRace {
    public debug : boolean = false;

    winningWayCount(index: number): number {
      throw new Error("Method not implemented.");
    }

    addRace(time: number, distance: number) {
      throw new Error("Method not implemented.");
    }

    
    constructor() {
    
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
