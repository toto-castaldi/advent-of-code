import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

const DIGIT_WORDS = {
  zero: '0', one: '1', two: '2', three: '3', four: '4',
  five: '5', six: '6', seven: '7', eight: '8', nine: '9'
} as const;

type DigitWord = keyof typeof DIGIT_WORDS;

async function main(spelledNumbers: boolean): Promise<number> {
  const currentDir = new URL('.', import.meta.url).pathname;
  const fileName = `${currentDir}input.txt`;
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  let sum = 0;
  for await (const line of lines) {
    sum += extractNum(spelledNumbers, line.trim().toLowerCase());
  }
  
  return sum;
}

function findDigitPositions(text: string): Map<number, string> {
  const positions = new Map<number, string>();
  
  for (let i = 0; i < text.length; i++) {
    if (isDigit(text[i])) {
      positions.set(i, text[i]);
    }
  }
  
  return positions;
}

function findSpelledDigitPositions(text: string): Map<number, string> {
  const positions = new Map<number, string>();
  
  for (const [word, digit] of Object.entries(DIGIT_WORDS)) {
    let index = text.indexOf(word);
    while (index !== -1) {
      positions.set(index, digit);
      index = text.indexOf(word, index + 1);
    }
  }
  
  return positions;
}

function isDigit(char: string): boolean {
  return char >= '0' && char <= '9';
}

function getFirstAndLastDigits(positions: Map<number, string>): [string | null, string | null] {
  if (positions.size === 0) {
    return [null, null];
  }
  
  const sortedPositions = Array.from(positions.keys()).sort((a, b) => a - b);
  const first = positions.get(sortedPositions[0]) ?? null;
  const last = positions.get(sortedPositions[sortedPositions.length - 1]) ?? null;
  
  return [first, last];
}

export function extractNum(spelledNumbers: boolean, text: string): number {
  const digitPositions = findDigitPositions(text);
  
  if (spelledNumbers) {
    const spelledPositions = findSpelledDigitPositions(text);
    for (const [pos, digit] of spelledPositions) {
      digitPositions.set(pos, digit);
    }
  }
  
  const [first, last] = getFirstAndLastDigits(digitPositions);
  
  if (!first || !last) {
    return 0;
  }
  
  return parseInt(first + last);
}

if (import.meta.main) {
  try {
    let result = await main(false);
    console.log(`Sum of all numbers: ${result}`);
    result = await main(true);
    console.log(`Sum of all numbers: ${result}`);
  } catch (error) {
    console.error("💥", error);
  }
}