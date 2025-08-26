import { readInputLines } from '../../src/main/typescript/utils.ts';

type SeedId = number;
type Range = {
  source : number,
  dest : number
  length : number
}

export enum Mapping {
    NONE,     
    seedToSoil,      
    soilToFertilizer,      
    fertilizerToWater,
    waterToLight,
    lightToTemperature,
    temperatureToHumidity,
    humidityToLocation     
}

type Mappings = Record<Mapping, Array<Range>>;

export class Gardener {

    private readonly seeds : Array<SeedId>;
    private readonly mappings : Mappings;
    public debug : boolean = false;

    findMapping(move: number, mappingKey: Mapping) : number {
      for (const range of this.mappings[mappingKey]) {
        if (move >= range.source && move <= range.source + range.length) {
          return range.dest + move - range.source;
        }
      }
      return move;
    }

    getLowestLocation(): number {
      let result = null;
      for (const seed of this.seeds) {
        if (this.debug) console.log(seed);
        let move = this.findMapping(seed, Mapping.seedToSoil);
        if (this.debug) console.log(`${seed} seed to soil ${move}`);
        move = this.findMapping(move, Mapping.soilToFertilizer);
        if (this.debug) console.log(`to fertilizer ${move}`);
        move = this.findMapping(move, Mapping.fertilizerToWater);
        if (this.debug) console.log(`to water ${move}`);
        move = this.findMapping(move, Mapping.waterToLight);
        if (this.debug) console.log(`to light ${move}`);
        move = this.findMapping(move, Mapping.lightToTemperature);
        if (this.debug) console.log(`to temp ${move}`);
        move = this.findMapping(move, Mapping.temperatureToHumidity);
        if (this.debug) console.log(`to humidity ${move}`);
        move = this.findMapping(move, Mapping.humidityToLocation);
        if (this.debug) console.log(`to location ${move}`);

        if (result === null || move < result) {
          result = move;
        }
      }
      if (result === null) throw new Error("can't find mapping");
      return result;
    }

    //50 98 2 -> source 98 len 2, dest 50 len 2
    map(line: string, mapping: Mapping) : void {
      const [dest, source, length] = line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim()).map( s => parseInt(s));
      this.mappings[mapping].push({ source, dest, length});
    }

    seedIds(line: string) : void  {
      line.split(" ").filter( s => s.trim().length > 0).map( s => s.trim() ).forEach( s => this.seeds.push(parseInt(s)));
    }

    constructor() {
      this.seeds = new Array<SeedId>();
      this.mappings = {
        [Mapping.NONE]: new Array<Range>(),
        [Mapping.seedToSoil]: new Array<Range>(),
        [Mapping.soilToFertilizer]: new Array<Range>(),
        [Mapping.fertilizerToWater]: new Array<Range>(),
        [Mapping.waterToLight]: new Array<Range>(),
        [Mapping.lightToTemperature]: new Array<Range>(),
        [Mapping.temperatureToHumidity]: new Array<Range>(),
        [Mapping.humidityToLocation]: new Array<Range>(),
      };
    }

    
}

if (import.meta.main) {
  

  let currentMapping = Mapping.NONE;

  try {
    const currentDir = new URL('.', import.meta.url).pathname;

    const gardener = new Gardener();
    for await (const line of readInputLines(`${currentDir}input.txt`)) {
      if (line.trim().length > 0) {
        if (line.trim().startsWith("seeds: ")) {
          gardener.seedIds(line.split("seeds: ")[1]);
        } else {
          if (line.trim().startsWith("seed-to-soil map:")) {
            currentMapping = Mapping.seedToSoil;
          } else if (line.trim().startsWith("soil-to-fertilizer map:")) {
            currentMapping = Mapping.soilToFertilizer;
          } else if (line.trim().startsWith("fertilizer-to-water map:")) {
            currentMapping = Mapping.fertilizerToWater;
          } else if (line.trim().startsWith("water-to-light map")) {
            currentMapping = Mapping.waterToLight;
          } else if (line.trim().startsWith("light-to-temperature map:")) {
            currentMapping = Mapping.lightToTemperature;
          } else if (line.trim().startsWith("temperature-to-humidity map:")) {
            currentMapping = Mapping.temperatureToHumidity;
          } else if (line.trim().startsWith("humidity-to-location map:")) {
            currentMapping = Mapping.humidityToLocation;
          } else {
            if (currentMapping !== Mapping.NONE) gardener.map(line, currentMapping);
          }
        }
      }
       
    }
    
    const part1Result = gardener.getLowestLocation();
    console.log(`Step 1: ${part1Result}`);

  } catch (error) {
    console.error("💥", error);
  }
}
