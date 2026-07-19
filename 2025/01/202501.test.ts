import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { Dial } from './202501.ts';

Deno.test("step one", () => {
  const dial = new Dial();
  dial.debug = true;
  
  assertEquals(dial.move("L68"), 82);
  assertEquals(dial.move("L30"), 52);
  assertEquals(dial.move("R48"), 0);
  assertEquals(dial.move("L5"), 95);
  assertEquals(dial.move("R60"), 55);
  assertEquals(dial.move("L55"), 0);
  assertEquals(dial.move("L1"), 99);
  assertEquals(dial.move("L99"), 0);
  assertEquals(dial.move("R14"), 14);
  assertEquals(dial.move("L82"), 32);

  assertEquals(dial.getZeroPassesCount(), 3);
});
