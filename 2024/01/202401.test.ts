import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { LocationIdList } from './202401.ts';

Deno.test("step one", () => {
  const locationIdList = new LocationIdList();
  locationIdList.debug = true;
  
  locationIdList.add(3,4);
  locationIdList.add(4,3);
  locationIdList.add(2,5);
  locationIdList.add(1,3);
  locationIdList.add(3,9);
  locationIdList.add(3,3);

  assertEquals(locationIdList.getSumOfDistances(), 11);
});