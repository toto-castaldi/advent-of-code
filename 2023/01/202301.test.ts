import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { extractNum } from './202301.ts';

Deno.test("extractNum - basic number extraction", () => {
  assertEquals(extractNum(false, "1abc2"), 12);
  assertEquals(extractNum(false, "pqr3stu8vwx"), 38);
  assertEquals(extractNum(false, "a1b2c3d4e5f"), 15);
  assertEquals(extractNum(false, "treb7uchet"), 77);
});

Deno.test("extractNum - step two", () => {
  assertEquals(extractNum(true, "two1nine"), 29);
  assertEquals(extractNum(true, "eightwothree"),83);
  assertEquals(extractNum(true, "abcone2threexyz"),13);
  assertEquals(extractNum(true, "xtwone3four"),24);
  assertEquals(extractNum(true, "4nineeightseven2"),42);
  assertEquals(extractNum(true, "zoneight234"),14);
  assertEquals(extractNum(true, "7pqrstsixteen"), 76);
});
