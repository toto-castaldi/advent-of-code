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
  
  const orderedHands = camelPoker.getOrderdHands();

  assertEquals(orderedHands.length, 5);

  console.log(orderedHands);

  assertEquals(orderedHands[0].cards, "32T3K");
  assertEquals(orderedHands[1].cards, "KTJJT");
  assertEquals(orderedHands[2].cards, "KK677");
  assertEquals(orderedHands[3].cards, "T55J5");
  assertEquals(orderedHands[4].cards, "QQQJA");

  assertEquals(camelPoker.getSumOfPoints(), 6440);
});


Deno.test("order", () => {
  const camelPoker = new CamelPoker();
  camelPoker.debug = true;
  
  camelPoker.addHand("44494 1");
  camelPoker.addHand("44484 1");
  
  const orderedHands = camelPoker.getOrderdHands();

  console.log(orderedHands);

  assertEquals(orderedHands[0].cards, "44484");
  assertEquals(orderedHands[1].cards, "44494");
});

