import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { Gardener } from './202305.ts';

Deno.test("step one", () => {
  const gardener = new Gardener();
  
  gardener.seedIds("79 14 55 13");

  gardener.seedToSoil("50 98 2");
  gardener.seedToSoil("52 50 48");

  gardener.soilToFertilizer("0 15 37");
  gardener.soilToFertilizer("37 52 2");
  gardener.soilToFertilizer("39 0 15");

  gardener.fertilizerToWater("49 53 8");
  gardener.fertilizerToWater("0 11 42");
  gardener.fertilizerToWater("42 0 7");
  gardener.fertilizerToWater("57 7 4");
  
  gardener.waterToLight("88 18 7");
  gardener.waterToLight("18 25 70");

  gardener.lightToTemperature("45 77 23");
  gardener.lightToTemperature("81 45 19");
  gardener.lightToTemperature("68 64 13");

  gardener.tempeatureToHumidity("0 69 1");
  gardener.tempeatureToHumidity("1 0 69");

  gardener.humidityToLocation("60 56 37");
  gardener.humidityToLocation("56 93 4");
  
  assertEquals(gardener.getLowestLocation(), 35);

});