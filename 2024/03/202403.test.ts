import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { MulComputer } from './202403.ts';

Deno.test("step one", () => {
  const mulComputer = new MulComputer();
  mulComputer.debug = true;
  
  mulComputer.add("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))");

  assertEquals(mulComputer.sumOfMultiplication(), 161);
});