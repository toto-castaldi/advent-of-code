import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { EngineSchema } from './202303.ts';

Deno.test("step one", () => {
  const engineSchema = new EngineSchema();

  engineSchema.addLine("467..114..");
  engineSchema.addLine("...*......");
  engineSchema.addLine("..35..633.");
  engineSchema.addLine("......#...");
  engineSchema.addLine("617*......");
  engineSchema.addLine(".....+.58.");
  engineSchema.addLine("..592.....");
  engineSchema.addLine("......755.");
  engineSchema.addLine("...$.*....");
  engineSchema.addLine(".664.598..");
  engineSchema.endLines();

  assertEquals(engineSchema.getSumOfParts(), 4361);

});

Deno.test("step two", () => {
  const engineSchema = new EngineSchema();

  engineSchema.addLine("467..114..");
  engineSchema.addLine("...*......");
  engineSchema.addLine("..35..633.");
  engineSchema.addLine("......#...");
  engineSchema.addLine("617*......");
  engineSchema.addLine(".....+.58.");
  engineSchema.addLine("..592.....");
  engineSchema.addLine("......755.");
  engineSchema.addLine("...$.*....");
  engineSchema.addLine(".664.598..");
  engineSchema.endLines();

  assertEquals(engineSchema.getSumOfGears(), 467835);

});

Deno.test("north - south", () => {
  const engineSchema = new EngineSchema();

  engineSchema.addLine("..592....#...64..*..........*........656..505..*......546.....&........422.........#............*.613.382..123............%....721...572....");
  engineSchema.addLine("...*.........*..........839.364.........*....*.549..............336...*...........129.......632....*....*.....#..647..122..25...............");
  engineSchema.addLine(".612...-..%............$..............628..489......................-.850..633.........=......*...972.660........*....*.....................");
  engineSchema.endLines();

  const gears = engineSchema.getGears();
  let otherGear = null;
  for (const gear of gears) {
    if (gear[0] == 613) {
      otherGear = gear[1];
      console.log("found !");
    } else if (gear[1] == 613) {
      otherGear = gear[0];
      console.log("found !");
    }
  }
  assertEquals(otherGear, 972);

});
