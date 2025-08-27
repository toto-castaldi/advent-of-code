import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { Gardener, Mapping } from './202305.ts';

Deno.test("step one", () => {
  const gardener = new Gardener();
  gardener.debug = true;
  
  gardener.seedIds("79 14 55 13");

  gardener.map("50 98 2", Mapping.seedToSoil);
  gardener.map("52 50 48", Mapping.seedToSoil);

  gardener.map("0 15 37", Mapping.soilToFertilizer);
  gardener.map("37 52 2", Mapping.soilToFertilizer);
  gardener.map("39 0 15", Mapping.soilToFertilizer);

  gardener.map("49 53 8", Mapping.fertilizerToWater);
  gardener.map("0 11 42", Mapping.fertilizerToWater);
  gardener.map("42 0 7", Mapping.fertilizerToWater);
  gardener.map("57 7 4", Mapping.fertilizerToWater);
  
  gardener.map("88 18 7", Mapping.waterToLight);
  gardener.map("18 25 70", Mapping.waterToLight);

  gardener.map("45 77 23", Mapping.lightToTemperature);
  gardener.map("81 45 19", Mapping.lightToTemperature);
  gardener.map("68 64 13", Mapping.lightToTemperature);

  gardener.map("0 69 1", Mapping.temperatureToHumidity);
  gardener.map("1 0 69", Mapping.temperatureToHumidity);

  gardener.map("60 56 37", Mapping.humidityToLocation);
  gardener.map("56 93 4", Mapping.humidityToLocation);
  
  assertEquals(gardener.getLowestLocationSingle(), 35);

});

Deno.test("step two", () => {
  const gardener = new Gardener();
  gardener.debug = true;
  
  gardener.seedIds("79 14 55 13");

  gardener.map("50 98 2", Mapping.seedToSoil);
  gardener.map("52 50 48", Mapping.seedToSoil);

  gardener.map("0 15 37", Mapping.soilToFertilizer);
  gardener.map("37 52 2", Mapping.soilToFertilizer);
  gardener.map("39 0 15", Mapping.soilToFertilizer);

  gardener.map("49 53 8", Mapping.fertilizerToWater);
  gardener.map("0 11 42", Mapping.fertilizerToWater);
  gardener.map("42 0 7", Mapping.fertilizerToWater);
  gardener.map("57 7 4", Mapping.fertilizerToWater);
  
  gardener.map("88 18 7", Mapping.waterToLight);
  gardener.map("18 25 70", Mapping.waterToLight);

  gardener.map("45 77 23", Mapping.lightToTemperature);
  gardener.map("81 45 19", Mapping.lightToTemperature);
  gardener.map("68 64 13", Mapping.lightToTemperature);

  gardener.map("0 69 1", Mapping.temperatureToHumidity);
  gardener.map("1 0 69", Mapping.temperatureToHumidity);

  gardener.map("60 56 37", Mapping.humidityToLocation);
  gardener.map("56 93 4", Mapping.humidityToLocation);
  
  assertEquals(gardener.getLowestLocationRanges(), 46);

});