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
