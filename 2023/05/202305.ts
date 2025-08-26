import { readInputLines } from '../../src/main/typescript/utils.ts';

type SeedId = number;
type Range = {
  source : number,
  dest : number
  lenght : number
}


//type CardsInfo = Record<CardId, MatchCount>;
//type Seeds = Array<SeedId>;

export class Gardener {

    private seeds : Array<SeedId>;
    private seedToSoilMapping : Array<Range>;
    private humidityToLocationMapping : Array<Range>;
    private temperatureToHumidityMapping : Array<Range>;
    private lightToTemperatureMapping : Array<Range>;
    private waterToLightMapping : Array<Range>;
    private fertilizerToWaterMapping : Array<Range>;
    private soilToFertilizerMapping : Array<Range>;

    findMapping(move: number, mapping: Range[]) : number {
      for (const range of mapping) {
        if (move >= range.source && move <= range.source + range.lenght) {
          return range.dest + move - range.source;
        }
      }
      return move;
    }

    getLowestLocation(): number {
      let result = null;
      for (const seed of this.seeds) {
        console.log(seed);
        let move = this.findMapping(seed, this.seedToSoilMapping);
        console.log(`${seed} seed to soil ${move}`);
        move = this.findMapping(move, this.soilToFertilizerMapping);
        console.log(`to fertilizer ${move}`);
        move = this.findMapping(move, this.fertilizerToWaterMapping);
        console.log(`to water ${move}`);
        move = this.findMapping(move, this.waterToLightMapping);
        console.log(`to light ${move}`);
        move = this.findMapping(move, this.lightToTemperatureMapping);
        console.log(`to temp ${move}`);
        move = this.findMapping(move, this.temperatureToHumidityMapping);
        console.log(`to humidity ${move}`);
        move = this.findMapping(move, this.humidityToLocationMapping);
        console.log(`to location ${move}`);

        if (result === null || move < result) {
          result = move;
        }
      }
      if (result === null) throw new Error("can't find mapping");
      return result;
    }

    humidityToLocation(line: string) : void {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.humidityToLocationMapping.push({ source, dest, lenght});
    }

    tempeatureToHumidity(line: string) : void {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.temperatureToHumidityMapping.push({ source, dest, lenght});
    }

    lightToTemperature(line: string) : void  {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.lightToTemperatureMapping.push({ source, dest, lenght});
    }

    waterToLight(line: string) : void  {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.waterToLightMapping.push({ source, dest, lenght});
    }

    fertilizerToWater(line: string) : void  {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.fertilizerToWaterMapping.push({ source, dest, lenght});
    }

    soilToFertilizer(line: string) : void  {
     const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.soilToFertilizerMapping.push({ source, dest, lenght});
    }

    //50 98 2 -> source 98 len 2, dest 50 len 2
    seedToSoil(line: string) : void {
      const [dest, source, lenght] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.seedToSoilMapping.push({ source, dest, lenght});
    }

    seedIds(line: string) : void  {
      line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim() ).forEach( s => this.seeds.push(parseInt(s)));
    }

    //private readonly cardsInfo: CardsInfo;
    //private readonly cache: Cache;

    constructor() {
      this.seeds = new Array<SeedId>();
      this.seedToSoilMapping = new Array<Range>();
      this.humidityToLocationMapping = new Array<Range>();
      this.temperatureToHumidityMapping = new Array<Range>();
      this.lightToTemperatureMapping = new Array<Range>();
      this.waterToLightMapping = new Array<Range>();
      this.fertilizerToWaterMapping = new Array<Range>();
      this.soilToFertilizerMapping = new Array<Range>();
    }

    
}

if (import.meta.main) {
  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const gardener = new Gardener();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
        //scratchCards.addCard(line);
    }
    
    const part1Result = gardener.getLowestLocation();
    console.log(`Step 1: ${part1Result}`);

  } catch (error) {
    console.error("💥", error);
  }
}
