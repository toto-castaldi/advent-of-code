import { assertEquals } from "https://deno.land/std@0.208.0/assert/mod.ts";
import { CamelPoker } from './202307.ts';

Deno.test("step one", () => {
  const camelPoker = new CamelPoker();
  camelPoker.debug = true;
  
  camelPoker.addHand("32T3K 765");
  camelPoker.addHand("T55J5 684");
  camelPoker.addHand("KK677 28");
  camelPoker.addHand("KTJJT 220");
  camelPoker.addHand("QQQJA 483");

  assertEquals(camelPoker.getSumOfOriginalPoints(), 6440);
});

Deno.test("step two", () => {
  const camelPoker = new CamelPoker();
  camelPoker.debug = true;
  
  camelPoker.addHand("32T3K 765");
  camelPoker.addHand("T55J5 684");
  camelPoker.addHand("KK677 28");
  camelPoker.addHand("KTJJT 220");
  camelPoker.addHand("QQQJA 483");

  assertEquals(camelPoker.getSumOfBestVersionPoints(), 5905);
});