import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { BoatRace } from './202306.ts';

Deno.test("step one", () => {
  const boatRace = new BoatRace();
  boatRace.debug = true;
  
  boatRace.addRace(7,9);
  boatRace.addRace(15,40);
  boatRace.addRace(30,200);
  
  assertEquals(boatRace.winningWayCount(2), 9);
  assertEquals(boatRace.winningWayCount(0), 4);
  assertEquals(boatRace.winningWayCount(1), 8);

});

Deno.test("step two", () => {
  const boatRace = new BoatRace();
  boatRace.debug = true;
  
  boatRace.addRace(71530,940200);
  
  assertEquals(boatRace.winningWayCount(0), 71503);

});

