import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { LevelReports } from './202402.ts';

Deno.test("step one", () => {
  const levelReports = new LevelReports();
  levelReports.debug = true;
  
  levelReports.add("7 6 4 2 1");
  levelReports.add("1 2 7 8 9");
  levelReports.add("9 7 6 2 1");
  levelReports.add("1 3 2 4 5");
  levelReports.add("8 6 4 4 1");
  levelReports.add("1 3 6 7 9");

  assertEquals(levelReports.countSafe(), 2);
});

Deno.test("step two", () => {
  const levelReports = new LevelReports();
  levelReports.debug = true;
  
  levelReports.add("7 6 4 2 1");
  levelReports.add("1 2 7 8 9");
  levelReports.add("9 7 6 2 1");
  levelReports.add("1 3 2 4 5");
  levelReports.add("8 6 4 4 1");
  levelReports.add("1 3 6 7 9");

  assertEquals(levelReports.countSafeTolerate(), 4);
});