import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { isGamePossible } from './202302.ts';

Deno.test("extractNum - step one", () => {
  const bagConfig = {
    red : 12,
    green : 13,
    blue : 14,
  };

  
  assertEquals(isGamePossible(bagConfig, "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"), false);
  assertEquals(isGamePossible(bagConfig, "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"), true);
  assertEquals(isGamePossible(bagConfig, "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"), true);
  
  assertEquals(isGamePossible(bagConfig, "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"), false);
  assertEquals(isGamePossible(bagConfig, "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"), true);

});
