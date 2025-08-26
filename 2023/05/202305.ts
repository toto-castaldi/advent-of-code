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
  enum Mapping {
    NONE,     
    seedToSoil,      
    soilToFertilizer,      
    fertilizerToWater,
    waterToLight,
    lightToTemperature,
    tempeatureToHumidity,
    humidityToLocation     
  }

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
            currentMapping = Mapping.tempeatureToHumidity;
          } else if (line.trim().startsWith("humidity-to-location map:")) {
            currentMapping = Mapping.humidityToLocation;
          } else {
            switch (currentMapping) {
              case Mapping.NONE: console.warn("strnge to be here..."); break;
              case Mapping.seedToSoil: gardener.seedToSoil(line); break;
              case Mapping.soilToFertilizer: gardener.soilToFertilizer(line); break;
              case Mapping.fertilizerToWater: gardener.fertilizerToWater(line); break;
              case Mapping.waterToLight: gardener.waterToLight(line); break;
              case Mapping.lightToTemperature: gardener.lightToTemperature(line); break;
              case Mapping.tempeatureToHumidity: gardener.tempeatureToHumidity(line); break;
              case Mapping.humidityToLocation: gardener.humidityToLocation(line); break;
              default: break;
            }
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
