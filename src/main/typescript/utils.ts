import { TextLineStream } from 'https://deno.land/std/streams/mod.ts'

// input = "467..*..114.."
// Output: [
//   { value: '467', position: 0 },
//   { value: '114', position: 8 }
// ]
export function findNumbers(str: string): Array<{value: number, position: number}> {
  const regex = /\d+/g;
  const matches = str.matchAll(regex);
  const results = [];
  
  for (const match of matches) {
    results.push({
      value: parseInt(match[0]),
      position: match.index!
    });
  }
  
  return results;
}

export function isDigit(char: string): boolean {
  return /[0-9]/.test(char);
}

export function containsOnlyDotsOrNumbers(arr: string[]): boolean {
  return arr.every(item => /^[\d.]+$/.test(item));
}

export async function* readInputLines(fileName : string): AsyncGenerator<string> {
  
  const file = await Deno.open(fileName, { read: true });
  
  const lines = file
    .readable
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new TextLineStream());
  
  for await (const line of lines) {
    yield line;
  }
}